var svg = d3.select("svg"),
    width = +svg.attr("width"),
    height = +svg.attr("height");

var projection = d3.geoAlbersUsa()
	.scale(1000)
	.translate([width / 2, height / 2]);

var DataExplorationUtility = d3.map();

var path = d3.geoPath()
	.projection(projection);

var x = d3.scaleLinear()
    .domain([1, 35])
    .rangeRound([0, 400]);

var color = d3.scaleThreshold()
    .domain([1,5,10,15,20,25,30,35])
    .range(d3.schemeBlues[8]);

var g = svg.append("g")
    .attr("class", "key")
    .attr("transform", "translate(0,40)");

g.selectAll("rect")
  .data(color.range().map(function(d) {
      d = color.invertExtent(d);
      if (d[0] == null) d[0] = x.domain()[0];
      if (d[1] == null) d[1] = x.domain()[1];
      return d;
    }))
  .enter().append("rect")
    .attr("height", 8)
    .attr("x", function(d) { return x(d[0]); })
    .attr("width", function(d) { return x(d[1]) - x(d[0]); })
    .attr("fill", function(d) { return color(d[0]); });

g.append("text")
    .attr("class", "caption")
    .attr("x", x.range()[0])
    .attr("y", -6)
    .attr("fill", "#000")
    .attr("text-anchor", "start")
    .attr("font-weight", "bold")
    .text("Total Sales");

g.call(d3.axisBottom(x)
    .tickSize(13)
    .tickFormat(function(x, i) { return i ? x : x; })
    .tickValues(color.domain()))
  .select(".domain")
    .remove();

d3.queue()
	.defer(d3.json, "/csj/us.json")
	.defer(d3.json, "DataExplorationUtility?op=TotalSalesCount") // Load DataExploration json
	.await(ready);

function ready(error, us, DataExplorationUtility) {
  if (error) throw error;
  var rateById = {}; // Create empty object for holding dataset
  DataExplorationUtility.forEach(function(d) {
	rateById[d.id] = +d.TotalProductSales;
  });
  var state = {};
  DataExplorationUtility.forEach(function(d){
	  state[d.id] = JSON.stringify(d._id);
  });
  var revCount = {};
  DataExplorationUtility.forEach(function(d){
	  revCount[d.id] = d.TotalProductSales;
  });
  var revMin = {};
  DataExplorationUtility.forEach(function(d){
	  revMin[d.id] = d.ReviewMin;
  });
  var revMax = {};
  DataExplorationUtility.forEach(function(d){
	  revMax[d.id] = d.ReviewMax;
  });
  var revAvg = {};
  DataExplorationUtility.forEach(function(d){
	  revAvg[d.id] = d.ReviewAvg;
  });
  svg.append("g")
      .attr("class", "counties")
			.selectAll("path")
			.data(topojson.feature(us, us.objects.counties).features)
			.enter().append("path")
			.attr("d", path)
			.style("fill", function(d) {
				return color(rateById[d.id]); // get rate value for property matching data ID
				// pass rate value to color function, return color based on domain and range
			})
			.style("stroke", "steelblue")
			.on("mouseover", function(d){
				if (typeof(revCount[d.id]) != "undefined"){
					document.getElementById("prodCount").innerHTML = "State: " + state[d.id] + "\tTotal Products:" + revCount[d.id] + "\tMinimum Rating: " + revMin[d.id] + "\tMaximum Rating: " + revMax[d.id] + "\tAverage Rating: " + revAvg[d.id].toFixed(2);;			
				}
				else{
					document.getElementById("prodCount").innerHTML = "State: N/A\tTotal Products: 0\tMinimum Rating: 0\tMaximum Rating: 0\tAverage Rating: 0";		
				}
				d3.select(this).attr("class","hover");
			})
			.on("mouseout", function(d){
				document.getElementById("prodCount").innerHTML = "State: N/A\tTotal Products: 0\tMinimum Rating: 0\tMaximum Rating: 0\tAverage Rating: 0";	
				d3.select(this).attr("class","incident");
			})
			.append("title")
			.text(function(d) { return revCount[d.id]; });

  svg.append("path")
      .datum(topojson.mesh(us, us.objects.states, function(a, b) { return a !== b; }))
      .attr("class", "states")
      .attr("d", path);
}