function init() {
	completeField = document.getElementById("searchId");
	completeTable = document.getElementById("complete-table");
	autoRow = document.getElementById("auto-row");
}

function doCompletion() {
	var url = "http://localhost/csj/Controller/AjaxUtility?action=complete&searchId=" + escape(completeField.value);
	req = initRequest();
	req.open("GET",url,false);
	req.setRequestHeader("Content-type", "text/xml");	
	req.send(); 
	req.onreadystatechange = callback();	
}

function initRequest() {
	if(window.XMLHttpRequest) {
		if(navigator.userAgent.indexOf('MSIE')!=-1){
			isIE = true;
		}		
		return new XMLHttpRequest();
	}
	else if(window.ActiveXObject){
		isIE = true;
		return new ActiveXObject("Microsoft.XMLHTTP");
	}
}

function appendProduct(productName,productId,productType){
	completeTable.style.display = 'table';
    var row = completeTable.insertRow(0);
    var cell = row.insertCell(0);
    var linkElement;
    linkElement = document.createElement("a");
    linkElement.setAttribute("href","/csj/Controller/DisplayProduct?id=" + productId + "&type=" + productType);
    //linkElement.setAttribute("style","margin:5px;padding:5px;");
    linkElement.appendChild(document.createTextNode(productName));
    cell.appendChild(linkElement);    
}

function parseMessages(responseXML) {
	if(responseXML == null){		
		return false;
	}
	else {
		var products = responseXML.getElementsByTagName("products")[0];
		if(products.childNodes.length > 0){
			completeTable.setAttribute("bordercolor","#DCDCDC");
			completeTable.setAttribute("border","1");			
			for(loop = 0;loop < products.childNodes.length;loop++){
				var product = products.childNodes[loop];
				var productName = product.getElementsByTagName("productName")[0];
				var productId = product.getElementsByTagName("id")[0];
				var productType = product.getElementsByTagName("productType")[0];
				appendProduct(productName.childNodes[0].nodeValue,productId.childNodes[0].nodeValue,productType.childNodes[0].nodeValue);
			}
		}
	}
}

function clearTable() {
	if(completeTable.getElementsByTagName("tr").length > 0){
		completeTable.style.display = 'none';
		for(loop = completeTable.childNodes.length - 1;loop >= 0;loop--){
			completeTable.removeChild(completeTable.childNodes[loop]);
		}
	}
}

function callback() {
	clearTable();
	if(req.readyState == 4){		
		if(req.status == 200){			
			parseMessages(req.responseXML);
			document.getElementById('complete-table').style.backgroundColor = "#F5F5F5";
			document.getElementById('complete-table').style.width = "220px";
			document.getElementById("complete-table").style.borderRadius = "6px";
			document.getElementById('complete-table').style.fontSize = "13px";
			document.getElementById('complete-table').style.padding = "4px";
			document.getElementById('complete-table').style.border = "thin solid #DCDCDC";
		}
	}
}
