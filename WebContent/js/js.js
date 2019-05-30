var http_request = false;
function createRequest(url) {
	
	http_request = false;
	if (window.XMLHttpRequest) { 
		http_request = new XMLHttpRequest();
		if (http_request.overrideMimeType) {
			http_request.overrideMimeType("text/xml");
		}
	} else if (window.ActiveXObject) {
		try {
			http_request = new ActiveXObject("Msxml2.XMLHTTP");
		} catch (e) {
			try {
				http_request = new ActiveXObject("Microsoft.XMLHTTP");

		   } catch (e) {}
		}
	}
	if (!http_request) {
		alert("http_request !");
		return false;
	}
	inputField = document.getElementById("queryString");            
	keyTable = document.getElementById("key_table");
	memorDiv = document.getElementById("pop");
	keyTableBody = document.getElementById("key_table_body");
	if (inputField.value.length > 0) {
		//var url = "operationServlet?info=soWord&key=" + inputField.value;   
		var url = "getKey.jsp?key=" + inputField.value;   
	                    
		http_request.open("GET", url, true);
		http_request.onreadystatechange = callTool;    
		http_request.send(null);
	} else {
		clearTool();
	}	
}

function callTool() {
	if (http_request.readyState == 4) {
		if (http_request.status == 200) {
			if(http_request.responseXML==null){                	
			var key = http_request.responseXML.getElementsByTagName("mykey")[0].firstChild.data;			
				clearTool();
			}else{
				setKeys(http_request.responseXML.getElementsByTagName("mykey"));
			}
		} else if (http_request.status == 204){
			clearTool();
		}
	}
}

function setKeys(the_keys) {            
	clearTool();
	var size = the_keys.length;
	setOffsets();
	var row, cell, txtNode;
	for (var i = 0; i < size; i++) {
		var nextNode = the_keys[i].firstChild.data;
		row = document.createElement("tr");
		cell = document.createElement("td");
		cell.onmouseout = function() {this.className='mouseOver';this.style.background='#FFFAFA';this.style.color='#000000';};
		cell.onmouseover = function() {this.className='mouseOut';this.style.background='#9D741C';this.style.color='#FFFFFF';};
		cell.setAttribute("border", "0");
		cell.onclick = function() { assignKey(this); } ;  
		cell.onkeydown=function(){if(event.keyCode==13){assignKey(this);}};                           
		txtNode = document.createTextNode(nextNode);
		cell.appendChild(txtNode);
		row.appendChild(cell);
		keyTableBody.appendChild(row);
	}

	if(size>0){		
		ObjTable.Load("key_table","TD");
		var oPrev = ObjTable.getNode(0,0);
		iCurRow=0;
		iCurCol=0;

		ObjTable.curRow = iCurRow;
		ObjTable.curCol = iCurCol;
		oPrev.focus();
		oPrev.bgColor ="#9D741C";
		oPrev.style.color="#FFFFFF";
	}
		
}

function setOffsets() {
	var end = inputField.offsetWidth;
	var left =calculateOffset(inputField, "offsetLeft")
	 var top = calculateOffset(inputField, "offsetTop") + inputField.offsetHeight;
	memorDiv.style.border = "black 1px solid";
	memorDiv.style.left = left + "px";
	memorDiv.style.top = top + "px";
	keyTable.style.width = end + "px";
}

function calculateOffset(field, attr) {
  var offset = 0;
  while(field) {
	offset += field[attr]; 
	field = field.offsetParent;
  }
  return offset;
}

function assignKey(cell) {
	inputField.value = cell.firstChild.nodeValue;
	clearTool();
}

function clearTool() {
	var ind = keyTableBody.childNodes.length;
	for (var i = ind - 1; i >= 0 ; i--) {
		 keyTableBody.removeChild(keyTableBody.childNodes[i]);
	}
	memorDiv.style.border = "none";
}

var ObjTable = new Object();
var prevbool = true;
ObjTable.colCount=0;	
ObjTable.rowCount=0;	
ObjTable.Map = null;	
ObjTable.prevRow = -1;	
ObjTable.prevCol = -1;	
ObjTable.curRow=-1;	
ObjTable.curCol=-1;	
ObjTable.Element="TD";	
ObjTable.Load = function(a_sID,a_sTagName){	
	var iRowCount=0, iColCount=0, i, j, m, n, iIndex=0, iCount;
	var sTable = document.getElementById(a_sID);
	if (sTable!=null) {	
    	var tableMap = [];
    	iColCount=sTable.rows[0].cells.length;
		iRowCount = sTable.rows.length;	
    	document.getElementsByTagName('body')[0].onkeydown=ObjTable_moveFocus;	
    	ObjTable.colCount = iColCount;	
    	ObjTable.rowCount = iRowCount;	
		var aCols=null, iCell;
    	for (i=0; i<iRowCount; ++i) {
			aCols = new Array(iColCount);
        	tableMap.push(aCols);
    	}
    	for (i=0; i<iRowCount; ++i) {
    		iIndex=0;
    		for (j=0; j<iColCount; j+=iCell.colSpan) {
    			if (tableMap[i][j]==null) {
    				iCell = sTable.rows[i].cells[iIndex++];
    				for(m=i; m<i+iCell.rowSpan; ++m) {
    					for(n=j; n<j+iCell.colSpan; ++n) {tableMap[m][n] = i+','+j;}
    				}
    				tableMap[i][j] = iCell;
    			}
    		}
    	}
    	ObjTable.Map = tableMap;
    }
};

function ObjTable_moveFocus(event) {

	var e = event || window.event;
	var oNode = e.target || e.srcElement;
	var oNext = null;
	switch (e.keyCode) {
		case 38:
			oNext = CTable_getNextNode(0);
			break;
		case 13:
		
		case 40:
			oNext = CTable_getNextNode(1);
			break;
	}

	if (oNext) {
    		oNext.focus();
    		oNext.bgColor="#9D741C";
			oNext.style.color="#FFFFFF";
	}
}

ObjTable.getNode = function(a_iRow,a_iCol) {
	
	if (a_iRow<0 || a_iCol<0) {return null;}
	
	var oNode=null;
	var aMaps = ObjTable.Map, aTemps=null;
	var iRow = a_iRow, iCol=a_iCol;
	var sTemp="";
	
	while(true) {
		oNode = aMaps[iRow][iCol];
		sTemp=typeof(oNode);
		if (sTemp.toLowerCase()=="string") {
			aTemps=oNode.split(",");
			iRow = aTemps[0];
			iCol = aTemps[1];
		}
		else {break;}
	}
	return oNode;	
}

function CTable_getNextNode(a_key) {
	var oNode = null;
	var iCol = ObjTable.curCol ,iRow=ObjTable.curRow;
	var iRowCount = ObjTable.rowCount, iColCount=ObjTable.colCount;
	var aMaps = ObjTable.Map, aTemps=null;
	var iCurCol=iCol, iCurRow = iRow, iTemp;
	var sTemp="", sStr="";
	oNode = aMaps[iRow][iCol];
	if (typeof(oNode)=="string") {
		aTemps = oNode.split(",");
		iCurRow = aTemps[0];
		iCurCol = aTemps[1];
	}
	while (true) {
		switch(a_key) {
			case 0:
				iRow--;
				break;
			case 1:
				iRow++;
				break;
		}
		if (iRow<0) {iRow=iRowCount-1;}
		if (iRow>=iRowCount) {iRow=0;}
		if ((iCurRow == iRow) && (iCurCol == iCol)) {continue;}
		oNext = aMaps[iRow][iCol];
		sTemp=typeof(oNext);
		if (sTemp.toLowerCase()=="string") {
			aTemps=oNext.split(",");
			if ((iCurRow!=aTemps[0]) || (iCurCol!=aTemps[1])){break;}
		continue;
		}
		break;
	}; 
	switch(a_key) {
		case 0:
		case 1:
			if (oNext.colSpan>1) {
				ObjTable.prevCol = CTable.curCol;
				ObjTable.prevRow = ObjTable.curRow;
				ObjTable.curRow = iRow;
			}
			else {
				ObjTable.prevCol = ObjTable.curCol;
				ObjTable.prevRow = ObjTable.curRow;
				ObjTable.curCol = iCol;
				ObjTable.curRow = iRow;
			}
			break;
	}
	RecallFocus(ObjTable);
	prevbool=false;
	iCurRow=ObjTable.curCol;
	iCurCol=ObjTable.curRow;
	return oNext;
}
function RecallFocus(c){
	var oPrev = ObjTable.getNode(c.prevRow,c.prevCol);
	if (oPrev) {
    	oPrev.bgColor="#FFFFFF";
		oPrev.style.color="#000000";
	}
}
