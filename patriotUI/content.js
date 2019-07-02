


const policies = [];

let currentIndex = -1;
let currentIsNew = false;
let actionTree = [{name:""}];
let conditionTree = [{name:""},{name:""}];
//during an edit, index will refer to policies list,
//actionTree and conditionTree will both point to that object



let actionArrays = {
  devices:["door1","door2","door3","door4"],
  commands:["on","off","open","close"],
  settings:["s1","s2","s3","s4"]
};

let conditionArrays = {
  devices:["FireSprinkler","WaterLeakSensor","VacationMode","MyPresence","HallwayMotionSensor"],
  states:["detected","not detected","on","off"],
  automationunits:["garage-door-opener","energy-saver","flood-alert","ready-for-rain"]
};



function setConfigFromFileData(json){
  actionArrays = json.actionArrays;
  conditionArrays = json.conditionArrays;
}

function setPoliciesFromFileData(json){
  for(let a=0;a<json.length;a++){
    policies.push(json[a]);
    createPolicyElement().children[0].children[0].children[0].innerHTML = json[a].text;
  }
}


function importFromFile(files,handler){
  if(files.length===0){ alert("Please select a file."); return;}
  if(files.length>1){ alert("Please select only one file."); return;}
  const objectURL = window.URL.createObjectURL(files[0]);
  fetch(objectURL).then(r=>r.json()).then(handler);
  window.URL.revokeObjectURL(objectURL);
}


function exportPoliciesToFile(){
  let dataStr = JSON.stringify(policies);
  var file = new Blob([dataStr], {type: "application/json"});
  var objectURL = window.URL.createObjectURL(file);
  
  var a = document.getElementById("exportpoliciesfile");
  a.href = objectURL;
  a.download = "policies.json";
  a.click();
  
  window.setTimeout(function() {
    window.URL.revokeObjectURL(objectURL); 
  }, 0);
}




function editPolicy(el,b){
  openPolicyEditingForm(getInd(el),b);
}
function deletePolicy(el){
  let warning = "Are you sure you want to delete this policy?\n\n";
  warning+=el.children[0].children[0].children[0].innerText;
  if(!confirm(warning)) return;
  
  policies.splice(getInd(el),1);
  document.getElementById("menusectionpolicylist").removeChild(el);
}


function createPolicyElement(){
  let newEl = document.createElement("DIV");
  newEl.classList.add("policyContainer");
  newEl.innerHTML = '<div class="card bg-info text-white"><div class="card-body"><div style="float:left;width:calc(100% - 135px);">[loading policy text...]</div><div style="float:right"><button class="btn btn-warning" onclick="editPolicy(this.parentElement.parentElement.parentElement.parentElement)">Edit</button> <button class="btn btn-warning" onclick="deletePolicy(this.parentElement.parentElement.parentElement.parentElement)">Delete</button></div></div></div><br>';
  document.getElementById("menusectionpolicylist").append(newEl);
  return newEl;
}

function newPolicy(){
  let newEl = createPolicyElement();
  policies.push({
    text:"",
    policyname:"",
    allowdeny:0,
    actionTree:[{name:""}],
    conditionTree:[{name:""},{name:""}]
  });
  editPolicy(newEl,true);
}

function getInd(el){
  let myClass = document.getElementsByClassName("policyContainer");
  for(let a=0;a<myClass.length;a++) if(myClass[a]===el) return a;
  return -1;
}




function updatePolicyFromTrees(){
  //given: policy trees are now set
  //so we need to update object, and the element text
  let form1 = document.getElementById("form1");
  
  //UPL string
  let str1 = "POLICY "+form1.policyname.value+":\n";
  let actionCode = constructActionCode(0);
  str1+=form1.allowdeny.value+" "+(actionCode===""?"EVERYTHING":actionCode);
  if(conditionTree[0].name!=="") 
    str1+="\nONLY IF "+constructConditionCode(0);
  if(conditionTree[1].name!=="")
    str1+="\nEXCEPT "+constructConditionCode(1);
  
  //natural language string
  let str2 = "<p class='font-weight-bold'>Policy: "+form1.policyname.value+"</p>";
  let actionL = constructActionLanguage(0);
  str2+= "<p>"+(form1.allowdeny.value==="ALLOW"?"Allow":"Deny")+" "+(actionL===""?"everything":actionL);
  if(conditionTree[0].name!=="") 
    str2+="<br>Only if "+constructConditionLanguage(0);
  if(conditionTree[1].name!=="")
    str2+="<br>Unless "+constructConditionLanguage(1);
  str2+="</p>";
  
  
  policies[currentIndex] = {
    text:str2,
    upl:str1,
    policyname:form1.policyname.value,
    allowdeny:form1.allowdeny.selectedIndex,
    actionTree:actionTree,
    conditionTree:conditionTree
  };
  document.getElementsByClassName("policyContainer")[currentIndex].children[0].children[0].children[0].innerHTML = str2;
}

function openPolicyEditingForm(ind,newForm){
  document.getElementById("menusection").style.display = "none";
  document.getElementById("buildersection").style.display = "block";
  currentIndex = ind;
  currentIsNew = newForm?true:false; //newform parameter might be absent
  
  if(!newForm){
    //reconstruct form 
    let form1 = document.getElementById("form1");
    form1.policyname.value = policies[ind].policyname;
    form1.allowdeny.selectedIndex = policies[ind].allowdeny;
    if(policies[ind].actionTree[0].name==="") 
      autoSetValue("a",0,"everything");
    else
      constructActionHTML(0,0);
    
    if(policies[ind].conditionTree[0].name!==""){
      addOnlyIfCondition();
      constructConditionHTML(0,0);
    }
    if(policies[ind].conditionTree[1].name!==""){
      addExceptCondition();
      constructConditionHTML(1,1);
    }
  }
  
}


function closePolicyEditingForm(){
  document.getElementById("buildersection").style.display = "none";
  document.getElementById("menusection").style.display = "block";
  currentIsNew = false;
  currentIndex = -1;
  actionTree = [{name:""}];
  conditionTree = [{name:""},{name:""}];
  document.getElementById("form1").policyname.value = "";
  document.getElementById("form1").allowdeny.selectedIndex = 0;
  clearAction();
  deleteOnlyIf();
  deleteExcept();
}




function autoSetValue(name,num,value){
  let el = document.getElementById("form1")["input"+name+num];
  el.value = value;
  if(el.type==="select-one") 
    el.onchange({srcElement:el});
  else 
    el.oninput();
}


function constructActionHTML(newind,treeind){
  let tree = policies[currentIndex].actionTree;
  switch(tree[treeind].name){
    case "device":
    case "command":
    case "setting":
      autoSetValue("a",newind,tree[treeind].name);
      autoSetValue("a",newind,tree[treeind].value);
      autoSetValue("a",actionTree[newind].c,tree[tree[treeind].c].value);
      break;
    case "and":
    case "or":
      autoSetValue("a",newind,tree[treeind].name);
      constructActionHTML(actionTree[newind].c1,tree[treeind].c1);
      constructActionHTML(actionTree[newind].c2,tree[treeind].c2);
      break;
    case "not":
      autoSetValue("a",newind,tree[treeind].name);
      constructActionHTML(actionTree[newind].c,tree[treeind].c);
      break;
  }
}

function constructConditionHTML(newind,treeind){
  let tree = policies[currentIndex].conditionTree;
  switch(tree[treeind].name){
    case "automationunit":
    case "time":
    case "date":
      autoSetValue("c",newind,tree[treeind].name);
      autoSetValue("c",newind,tree[treeind].value);
      autoSetValue("c",conditionTree[newind].c,tree[tree[treeind].c].value);
      break;
    case "devicestate":
      autoSetValue("c",newind,tree[treeind].name);
      autoSetValue("c",conditionTree[newind].c1,tree[tree[treeind].c1].value);
      autoSetValue("c",conditionTree[newind].c2,tree[tree[treeind].c2].value);
      autoSetValue("c",conditionTree[newind].c3,tree[tree[treeind].c3].value);
      break;
    case "devicevalue":
      autoSetValue("c",newind,tree[treeind].name);
      autoSetValue("c",newind,tree[treeind].value);
      autoSetValue("c",conditionTree[newind].c1,tree[tree[treeind].c1].value);
      autoSetValue("c",conditionTree[newind].c2,tree[tree[treeind].c2].value);
      break;
    case "since":
      autoSetValue("c",newind,tree[treeind].name);
      constructConditionHTML(conditionTree[newind].c1,tree[treeind].c1);
      constructConditionHTML(conditionTree[newind].c2,tree[treeind].c2);
      constructWithinHTML(   conditionTree[newind].c3,tree[treeind].c3);
      break;
    case "once":
    case "lastly":
      autoSetValue("c",newind,tree[treeind].name);
      constructConditionHTML(conditionTree[newind].c1,tree[treeind].c1);
      constructWithinHTML(   conditionTree[newind].c2,tree[treeind].c2);
      break;
    case "and":
    case "or":
      autoSetValue("c",newind,tree[treeind].name);
      constructConditionHTML(conditionTree[newind].c1,tree[treeind].c1);
      constructConditionHTML(conditionTree[newind].c2,tree[treeind].c2);
      break;
    case "not":
      autoSetValue("c",newind,tree[treeind].name);
      constructConditionHTML(conditionTree[newind].c,tree[treeind].c);
      break;
  }
}

function constructWithinHTML(newind,treeind){
  let node = policies[currentIndex].conditionTree[treeind];
  let els = document.getElementById("form1")["inputc"+newind];
  els[0].value = node.value1; els[0].oninput();
  els[1].value = node.value2; els[1].oninput();
}







function pushAction(){
  actionTree.push({name:""});
  return actionTree.length-1;
}

function pushCondition(){
  conditionTree.push({name:""});
  return conditionTree.length-1;
}

function constructActionCode(a){
  let node = actionTree[a];
  let recurse = constructActionCode;
  switch(node.name){
    case "isisnot": return node.value;
    case "device": return "action_device"+recurse(node.c)+node.value;
    case "command": return "action_command"+recurse(node.c)+node.value;
    case "setting": return "action_command_arg"+recurse(node.c)+node.value;
    case "and": return "("+recurse(node.c1)+") AND ("+recurse(node.c2)+")";
    case "or": return "("+recurse(node.c1)+") OR ("+recurse(node.c2)+")";
    case "not": return "NOT ("+recurse(node.c)+")";
    default: return "";
  }
}

function constructConditionCode(a){ //a=0, only if. a=1, except.
  let node = conditionTree[a];
  let recurse = constructConditionCode;
  switch(conditionTree[a].name){
    case "isisnot": return node.value;
    case "device": return node.value;
    case "state": return node.value;
    case "within": return "WITHIN ["+(node.value1<node.value2?(node.value1+", "+node.value2):(node.value2+", "+node.value1))+"]";
    case "operators": return translateOperators(node.value);
    case "automationunit": return "automation_unit"+recurse(node.c)+node.value;
    case "devicestate": return "state("+recurse(node.c1)+")"+recurse(node.c2)+recurse(node.c3);
    case "devicevalue": return "state("+recurse(node.c1)+")"+recurse(node.c2)+node.value;
    case "time": return "current_time"+recurse(node.c)+node.value+":00";
    case "date": return "current_date"+recurse(node.c)+invertDate(node.value);
    case "since": return "SINCE("+recurse(node.c1)+", "+recurse(node.c2)+") "+recurse(node.c3);
    case "once": return "ONCE("+recurse(node.c1)+") "+recurse(node.c2);
    case "lastly": return "LASTLY("+recurse(node.c1)+") "+recurse(node.c2);
    case "and": return "("+recurse(node.c1)+") AND ("+recurse(node.c2)+")";
    case "or": return "("+recurse(node.c1)+") OR ("+recurse(node.c2)+")";
    case "not": return "NOT ("+recurse(node.c)+")";
    default: return "";
  }
}

function invertDate(d){
  let ar = d.split("-");
  return ar[1]+"-"+ar[2]+"-"+ar[0];
}

function translateOperators(d){
  switch(d.substring(1,2)){
    case "=":                         return " = ";
    case String.fromCharCode(0x2260): return " != ";
    case ">":                         return " > ";
    case String.fromCharCode(0x2265): return " >= ";
    case "<":                         return " < ";
    case String.fromCharCode(0x2264): return " <= ";
  }
}


function constructActionLanguage(a){
  let node = actionTree[a];
  let recurse = constructActionLanguage;
  switch(node.name){
    case "isisnot": return node.value===' = '?" is ":" is not ";
    case "device": return "action_device"+recurse(node.c)+node.value;
    case "command": return "action_command"+recurse(node.c)+node.value;
    case "setting": return "action_command_arg"+recurse(node.c)+node.value;
    case "and": return "("+recurse(node.c1)+") and ("+recurse(node.c2)+")";
    case "or": return "("+recurse(node.c1)+") or ("+recurse(node.c2)+")";
    case "not": return "everything except ("+recurse(node.c)+")";
    default: return "";
  }
}

function constructConditionLanguage(a){
  let node = conditionTree[a];
  let recurse = constructConditionLanguage;
  switch(conditionTree[a].name){
    case "isisnot": return node.value===' = '?" is ":" is not ";
    case "device": return node.value;
    case "state": return node.value;
    case "within": return "between "+(node.value1<node.value2?(node.value1+" and "+node.value2):(node.value2+" and "+node.value1))+" seconds ago";
    case "operators": return node.value;
    case "automationunit": return "automation_unit"+recurse(node.c)+node.value;
    case "devicestate": return "state of "+recurse(node.c1)+recurse(node.c2)+recurse(node.c3);
    case "devicevalue": return "value of "+recurse(node.c1)+recurse(node.c2)+node.value;
    case "time": return "time"+recurse(node.c)+node.value;
    case "date": return "date"+recurse(node.c)+node.value;
    case "since": return "since ("+recurse(node.c1)+") then ("+recurse(node.c2)+") "+recurse(node.c3);
    case "once": return "("+recurse(node.c1)+") happened "+recurse(node.c2);
    case "lastly": return "("+recurse(node.c1)+") last happened "+recurse(node.c2);
    case "and": return "("+recurse(node.c1)+") and ("+recurse(node.c2)+")";
    case "or": return "("+recurse(node.c1)+") or ("+recurse(node.c2)+")";
    case "not": return "("+recurse(node.c)+") is not true";
    default: return "";
  }
}








function createSelectionStr(str,a,name,ar){
  let f = '<span>'+str+'<select name="'+name+'" onchange="'+a+'.value=this.value" required>'+
            '<option value=""></option>';
  ar.forEach(s=> f+='<option value="'+s+'">'+s+'</option>');
  f+='</select></span>';
  return f;
}


let actionStr = {

      begin: '<select required name="inputa0" onchange="actionChangeHandler(event,0)">'+
                    '<option value=""></option>'+
                    '<option value="everything">everything</option>'+
                    '<option value="device">device is/isn\'t</option>'+
                    '<option value="command">command is/isn\'t</option>'+
                    '<option value="setting">setting is/isn\'t</option>'+
                    '<option value="and">____ and _____</option>'+
                    '<option value="or">____ or _____</option>'+
                    '<option value="not">everything except ____</option>'+
                 '</select>',
                        
      general: function(a){return '<select required name="inputa'+a+'" onchange="actionChangeHandler(event,'+a+')">'+
                    '<option value=""></option>'+
                    '<option value="device">device is</option>'+
                    '<option value="command">command is</option>'+
                    '<option value="setting">setting is</option>'+
                    '<option value="and">____ and _____</option>'+
                    '<option value="or">____ or _____</option>'+
                    '<option value="not">everything except ____</option>'+
                 '</select>'}
};
                  
actionStr.isisnot = function(a){
  actionTree[a].name = "isisnot";
  actionTree[a].value = " = ";
  let name = "inputa"+a;
  return " <select name='"+name+"' onchange='actionTree["+a+"].value=this.value'><option value=' = '>is</option><option value=' != '>is not</option></select> ";
};
actionStr.device  = function(a){
  actionTree[a].name = "device";
  let b = actionTree[a].c = pushAction();
  let name = "inputa"+a;
  return createSelectionStr("device"+actionStr.isisnot(b),"actionTree["+a+"]",name,actionArrays.devices);
};
actionStr.command = function(a){
  actionTree[a].name = "command";
  let b = actionTree[a].c = pushAction();
  let name = "inputa"+a;
  return createSelectionStr("command"+actionStr.isisnot(b),"actionTree["+a+"]",name,actionArrays.commands);
};
actionStr.setting = function(a){
  actionTree[a].name = "setting";
  let b = actionTree[a].c = pushAction();
  let name = "inputa"+a;
  return createSelectionStr("setting"+actionStr.isisnot(b),"actionTree["+a+"]",name,actionArrays.settings);
};
                        
actionStr.and = function(a){
  actionTree[a].name = "and";
  let b1 = actionTree[a].c1 = pushAction();
  let b2 = actionTree[a].c2 = pushAction();
  return '<span class="spanbox"><span>'+actionStr.general(b1)+'</span> and <span>'+actionStr.general(b2)+'</span></span>';
};
actionStr.or  = function(a){
  actionTree[a].name = "or";
  let b1 = actionTree[a].c1 = pushAction();
  let b2 = actionTree[a].c2 = pushAction();
  return '<span class="spanbox"><span>'+actionStr.general(b1)+'</span> or <span>' +actionStr.general(b2)+'</span></span>';
};
actionStr.not = function(a){
  actionTree[a].name = "not";
  let b = actionTree[a].c = pushAction();
  return '<span class="spanbox">everything except <span>'+actionStr.general(b)+'</span></span>';
};


document.getElementById("actionclause").children[1].innerHTML = actionStr.begin;


function actionChangeHandler(e,a){
  let str = "";
  switch(e.srcElement.value){
    case "device":  str = actionStr.device(a);  break;
    case "command": str = actionStr.command(a); break;
    case "setting": str = actionStr.setting(a); break;
    case "and":     str = actionStr.and(a);     break;
    case "or":      str = actionStr.or(a);      break;
    case "not":     str = actionStr.not(a);     break;
    default: return;
  }
  e.srcElement.parentElement.innerHTML = str;
}

function clearAction(){
  document.getElementById("actionclause").children[1].innerHTML = actionStr.begin;
  actionTree[0] = {name:""};
}

























let conditionStr = {
    general: function(a){return '<select name="inputc'+a+'" required onchange="conditionChangeHandler(event,'+a+')">'+
       '<option value=""></option>'+
       '<option value="automationunit">automation unit is/isn\'t</option>'+
       '<option value="devicestate">State of [device] is/is\'t [state]</option>'+
       '<option value="devicevalue">Value of [device] &lt; / &gt; / = [number]</option>'+
       '<option value="time">time &lt; / &gt; / = [time]</option>'+
       '<option value="date">date &lt; / &gt; / = [date]</option>'+
       '<option value="since">since ____, ____ within [times]</option>'+
       '<option value="once">______ happened within [times]</option>'+
       '<option value="lastly"> _______ last happened within [times]</option>'+
       '<option value="or">____ or _____</option>'+
       '<option value="and">____ and _____</option>'+
       '<option value="not">____ is not true</option>'+
    '</select>';}
};

conditionStr.isisnot = function(a){
  conditionTree[a].name = "isisnot";
  conditionTree[a].value = " = ";
  let name = "inputc"+a;
  return " <select name='"+name+"' onchange='conditionTree["+a+"].value=this.value'><option value=' = '>is</option><option value=' != '>is not</option></select> ";
};
conditionStr.device = function(a){
  conditionTree[a].name = "device";
  let name = "inputc"+a;
  return createSelectionStr("","conditionTree["+a+"]",name,conditionArrays.devices);
};
conditionStr.state = function(a){
  conditionTree[a].name = "state";
  let name = "inputc"+a;
  return createSelectionStr("","conditionTree["+a+"]",name,conditionArrays.states);
};
conditionStr.within = function(a){
  conditionTree[a].name = "within";
  let name = "inputc"+a;
  return " between <input name='"+name+"' type='number' min='0' class='numinput' oninput='conditionTree["+a+"].value1=this.value' required> "+
         "and <input name='"+name+"' type='number' min='0' class='numinput' oninput='conditionTree["+a+"].value2=this.value' required> seconds ago";
};
conditionStr.operators = function(a){
  conditionTree[a].name = "operators";
  let name = "inputc"+a;
  return createSelectionStr("","conditionTree["+a+"]",name,[" = "," &#x2260; "," &gt; "," &#x2265; "," &lt; "," &#x2264; "]);
};
conditionStr.automationunit = function(a){
  conditionTree[a].name = "automationunit";
  let b = conditionTree[a].c = pushCondition();
  let name = "inputc"+a;
  return createSelectionStr("automation unit"+conditionStr.isisnot(b),"conditionTree["+a+"]",name,conditionArrays.automationunits);
};
conditionStr.devicestate = function(a){
  conditionTree[a].name = "devicestate";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  let b3 = conditionTree[a].c3 = pushCondition();
  return '<span>state of <span>'+conditionStr.device(b1)+'</span>'+conditionStr.isisnot(b2)+'<span>'+conditionStr.state(b3)+'</span></span>';
};
conditionStr.devicevalue = function(a){
  conditionTree[a].name = "devicevalue";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  let name = "inputc"+a;
  return '<span>Value of <span>'+conditionStr.device(b1)+'</span> '+conditionStr.operators(b2)+
      " <input name='"+name+"' type='number' class='numinput' oninput='conditionTree["+a+"].value=this.value' required></span>";
};
conditionStr.time = function(a){
  conditionTree[a].name = "time";
  let b = conditionTree[a].c = pushCondition();
  let name = "inputc"+a;
  return "<span>Time "+conditionStr.operators(b)+
        " <input name='"+name+"' type='time' class='numinput' oninput='conditionTree["+a+"].value=this.value' required></span>";
};
conditionStr.date = function(a){
  conditionTree[a].name = "date";
  let b = conditionTree[a].c = pushCondition();
  let name = "inputc"+a;
  return "<span>Date "+conditionStr.operators(b)+
        " <input name='"+name+"' type='date' class='numinput' oninput='conditionTree["+a+"].value=this.value' required></span>";
};

conditionStr.since = function(a){
  conditionTree[a].name = "since";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  let b3 = conditionTree[a].c3 = pushCondition();
  return "<span class='spanbox'>Since <span>"+conditionStr.general(b1)+"</span>, <span>"+conditionStr.general(b2)+"</span>"+conditionStr.within(b3)+"</span>";
};
conditionStr.once = function(a){
  conditionTree[a].name = "once";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  return "<span class='spanbox'><span>"+conditionStr.general(b1)+"</span> happened"+conditionStr.within(b2)+"</span>";
};
conditionStr.lastly = function(a){
  conditionTree[a].name = "lastly";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  return "<span class='spanbox'><span>"+conditionStr.general(b1)+"</span> last happened"+conditionStr.within(b2)+"</span>";
};

conditionStr.and = function(a){
  conditionTree[a].name = "and";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  return '<span class="spanbox"><span>'+conditionStr.general(b1)+'</span> and <span>'+conditionStr.general(b2)+'</span></span>';
};
conditionStr.or  = function(a){
  conditionTree[a].name = "or";
  let b1 = conditionTree[a].c1 = pushCondition();
  let b2 = conditionTree[a].c2 = pushCondition();
  return '<span class="spanbox"><span>'+conditionStr.general(b1)+'</span> or <span>' +conditionStr.general(b2)+'</span></span>';
};
conditionStr.not = function(a){
  conditionTree[a].name = "not";
  let b = conditionTree[a].c = pushCondition();
  return '<span class="spanbox"><span>'+conditionStr.general(b)+'</span> is not true</span>';
};


function addOnlyIfCondition(){
  document.getElementById("onlyifclause").innerHTML = "Only if <span style='line-height:40px;'>"+conditionStr.general(0)+"</span>"+
        "<br><input type='button' value='Clear expression' onclick='addOnlyIfCondition()' class='fadeIn'><input type='button' value='Delete \"only if\"' onclick='deleteOnlyIf()' class='fadeIn'>";
  conditionTree[0] = {name:""};
}

function deleteOnlyIf(){
  document.getElementById("onlyifclause").innerHTML = "<input type=\"button\" value='Add \"only if\"' onclick=\"addOnlyIfCondition()\" class=\"fadeIn\">";
  conditionTree[0] = {name:""};
}

function addExceptCondition(){
  document.getElementById("exceptclause").innerHTML = "Unless <span style='line-height:40px;'>"+conditionStr.general(1)+"</span>"+
        "<br><input type='button' value='Clear expression' onclick='addExceptCondition()' class='fadeIn'><input type='button' value='Delete \"unless\"' onclick='deleteExcept()' class='fadeIn'>";
  conditionTree[1] = {name:""};
}

function deleteExcept(){
  document.getElementById("exceptclause").innerHTML = "<input type=\"button\" value='Add \"unless\"' onclick=\"addExceptCondition()\" class=\"fadeIn\">";
  conditionTree[1] = {name:""};
}

function conditionChangeHandler(e,a){
  let str = "";
  switch(e.srcElement.value){
    case "automationunit":  str = conditionStr.automationunit(a);  break;
    case "devicestate":     str = conditionStr.devicestate(a);     break;
    case "devicevalue":     str = conditionStr.devicevalue(a);     break;
    case "time":            str = conditionStr.time(a);            break;
    case "date":            str = conditionStr.date(a);            break;
    case "since":           str = conditionStr.since(a);           break;
    case "once":            str = conditionStr.once(a);            break;
    case "lastly":          str = conditionStr.lastly(a);          break;
    case "or":              str = conditionStr.or(a);              break;
    case "and":             str = conditionStr.and(a);             break;
    case "not":             str = conditionStr.not(a);             break;
    default: return;
  }
  e.srcElement.parentElement.innerHTML = str;
}



function discardChanges(){
  if(currentIsNew){ 
    if(confirm("Discard policy?")){
      policies.pop(); //new will always be last
      let plist = document.getElementById("menusectionpolicylist");
      plist.removeChild(plist.lastChild);
      closePolicyEditingForm();
    }
  }else{
    if(confirm("Discard changes?")){
      closePolicyEditingForm();
    }
  }
}

let form1 = document.getElementById("form1");
form1.onsubmit = function(e){
  if(!checkPolicyName(form1.policyname.value)){
    alert("Please enter a valid policy name");
    e.preventDefault();
    return;
  }
  
  updatePolicyFromTrees();
  closePolicyEditingForm();
  e.preventDefault();
};

function checkPolicyName(str){
  if(str.length===0) return false;
  if(!isLetter(str.charAt(0))) return false;
  for(let a=0;a<str.length;a++)
    if(!(isLetter(str.charAt(a))||isDigit(str.charAt(a))))
      return false;
  return true;
}

function isLetter(c){
  return (c>='a'&&c<='z')||(c>='A'&&c<='Z');
}
function isDigit(c){
  return c>='0'&&c<='9';
}














