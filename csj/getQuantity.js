function updateParam(uri, key, value) {
  var re = new RegExp("([?&])" + key + "=.*?(&|$)", "i");
  var separator = uri.indexOf('?') !== -1 ? "&" : "?";
  if (uri.match(re)) {
    return uri.replace(re, '$1' + key + "=" + value + '$2');
  }
  else {
    return uri + separator + key + "=" + value;
  }
}
function getQuantity() {
    var x = document.getElementById("qnty").value;
    var y = document.getElementById("buynowLink").href;  
    var queryString = y.substring( y.indexOf('?') + 1 );
    var queries, temp, i, l;
  	queries = queryString.split("&");
  	var stat = 0,k;//false
  	for (i = 0, l = queries.length; i < l; i++) {
   		temp = queries[i].split('=');
    	if (temp[0] == "qnty") {
       		k = updateParam(y,"qnty",x)
       	}
        else{
        	k = y + "&qnty=" + x;
        }
  	}
    document.getElementById("buynowLink").href = k;
}