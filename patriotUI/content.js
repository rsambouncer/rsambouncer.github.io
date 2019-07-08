


const policies = [];
let viewmodeUPL = false;

let currentIndex = -1;
let currentIsNew = false;
let actionTree = [{name:""}];
let athead = 0;
let conditionTree = [{name:""},{name:""}];
let cthead1 = 0;
let cthead2 = 1;
//during an edit, index will refer to policies list,
//actionTree and conditionTree will both point to that object



let actionArrays = {
  devices:["door1","door2","door3","door4"],
  commands:["on","off","open","close"]
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
    createPolicyElement();
    updatePolicyTextBox(policies.length-1,json[a].text,json[a].upl);
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



function showPoliciesAsUPL(){
  viewmodeUPL = true;
  for(let a=0;a<policies.length;a++)
    updatePolicyTextBox(a,policies[a].text,policies[a].upl);
  document.getElementById("switchPolicyViewBtn").innerHTML = "Show Current Policies as Human-Readable Text";
  document.getElementById("switchPolicyViewBtn").onclick = showPoliciesAsNL;
}

function showPoliciesAsNL(){
  viewmodeUPL = false;
  for(let a=0;a<policies.length;a++)
    updatePolicyTextBox(a,policies[a].text,policies[a].upl);
  document.getElementById("switchPolicyViewBtn").innerHTML = "Show Current Policies as UPL Strings";
  document.getElementById("switchPolicyViewBtn").onclick = showPoliciesAsUPL;
}

showPoliciesAsNL();


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

function updatePolicyTextBox(ind, text, upl){
  let el = document.getElementsByClassName("policyContainer")[ind].children[0].children[0].children[0];
  
  if(viewmodeUPL) el.innerText = upl;
  else el.innerHTML = text;
}


function newPolicy(){
  let newEl = createPolicyElement();
  policies.push({
    text:"",
    policyname:"",
    allowdeny:0,
    actionTree:[{name:""}],
    conditionTree:[{name:""},{name:""}],
    athead:0,
    cthead1:0,
    cthead2:0
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
  let actionCode = constructActionCode(athead);
  str1+=form1.allowdeny.value+" "+(actionCode===""?"EVERYTHING":actionCode);
  if(conditionTree[cthead1].name!=="") 
    str1+="\nONLY IF "+constructConditionCode(cthead1);
  if(conditionTree[cthead2].name!=="")
    str1+="\nEXCEPT "+constructConditionCode(cthead2);
  
  //natural language string
  let str2 = "<p class='font-weight-bold'>Policy: "+form1.policyname.value+"</p>";
  let actionL = constructActionLanguage(athead);
  str2+= "<p>"+(form1.allowdeny.value==="ALLOW"?"Allow":"Deny")+" "+(actionL===""?"everything":actionL);
  if(conditionTree[cthead1].name!=="") 
    str2+="<br>Only if "+constructConditionLanguage(cthead1);
  if(conditionTree[cthead2].name!=="")
    str2+="<br>Unless "+constructConditionLanguage(cthead2);
  str2+="</p>";
  
  
  policies[currentIndex] = {
    text:str2,
    upl:str1,
    policyname:form1.policyname.value,
    allowdeny:form1.allowdeny.selectedIndex,
    actionTree:actionTree,
    conditionTree:conditionTree,
    athead:athead,
    cthead1:cthead1,
    cthead2:cthead2
  };
  updatePolicyTextBox(currentIndex,str2,str1);
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
    if(policies[ind].actionTree[policies[ind].athead].name==="") 
      autoSetValue("a",0,"everything");
    else
      constructActionHTML(0,policies[ind].athead);
    
    if(policies[ind].conditionTree[policies[ind].cthead1].name!==""){
      addOnlyIfCondition();
      constructConditionHTML(0,policies[ind].cthead1);
    }
    if(policies[ind].conditionTree[policies[ind].cthead2].name!==""){
      addExceptCondition();
      constructConditionHTML(1,policies[ind].cthead2);
    }
    
    //disabled when action clause is blank
    document.getElementById("form1").submit.disabled = false;
  }
  
}


function closePolicyEditingForm(){
  document.getElementById("buildersection").style.display = "none";
  document.getElementById("menusection").style.display = "block";
  document.getElementById("form1").policyname.value = "";
  document.getElementById("form1").allowdeny.selectedIndex = 0;
  currentIsNew = false;
  currentIndex = -1;
  actionTree = [];
  conditionTree = [];
  clearAction();
  deleteOnlyIf();
  deleteExcept();
  resetCollapsables();
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
      autoSetValue("a",newind,tree[treeind].name);
      autoSetValue("a",newind,tree[treeind].value);
      autoSetValue("a",actionTree[newind].c,tree[tree[treeind].c].value);
      break;
    case "setting":
      autoSetValue("a",newind,tree[treeind].name);
      autoSetValue("a",actionTree[newind].c1,tree[tree[treeind].c1].value);
      constructValuechoice(actionTree[newind].c2,tree[treeind].c2);
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

function constructValuechoice(newind,treeind){
  let node = policies[currentIndex].actionTree[treeind];
  let el = document.getElementById("form1")["inputa"+newind];
  el.value = node.value1; el.onchange();
  el = document.getElementById("form1")["inputa"+newind];
  el.value = node.value2; 
  if(el.onchange) el.onchange();
  if(el.oninput) el.oninput();
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
    case "operators": return translateOperators(node.value);
    case "valuechoice": return (node.value1==="time"?node.value2+":00":node.value1==="date"?invertDate(node.value2):node.value2);
    case "device": return "action_device"+recurse(node.c)+node.value;
    case "command": return "action_command"+recurse(node.c)+node.value;
    case "setting": return "action_command_arg"+recurse(node.c1)+recurse(node.c2);
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
    case "operators": return node.value;
    case "valuechoice": return node.value2;
    case "device": return "device"+recurse(node.c)+node.value;
    case "command": return "command"+recurse(node.c)+node.value;
    case "setting": return "command input"+recurse(node.c1)+recurse(node.c2);
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
    case "automationunit": return "automation unit"+recurse(node.c)+node.value;
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


document.getElementById("actioncollapsebutton").onclick = function(){
  document.getElementById("actioncollapsebutton").style.display = "none";
  document.getElementById("onlyifcollapsebutton").style.display = "inline-block";
  document.getElementById("form1").submit.disabled = false;
};

document.getElementById("onlyifcollapsebutton").onclick = function(){
  document.getElementById("onlyifcollapsebutton").style.display = "none";
  document.getElementById("exceptcollapsebutton").style.display = "inline-block";
};

document.getElementById("exceptcollapsebutton").onclick = function(){
  document.getElementById("exceptcollapsebutton").style.display = "none";
  document.getElementById("dummycollapsebutton").style.display = "inline-block";
};




function resetCollapsables(){
  document.getElementById("actionclausewrapper").classList.remove("show");
  document.getElementById("onlyifclausewrapper").classList.remove("show");
  document.getElementById("exceptclausewrapper").classList.remove("show");
  document.getElementById("actioncollapsebutton").style.display = "inline-block";
  document.getElementById("onlyifcollapsebutton").style.display = "none";
  document.getElementById("exceptcollapsebutton").style.display = "none";
  document.getElementById("dummycollapsebutton").style.display = "none";
  document.getElementById("form1").submit.disabled = true;
}


function createSelectionStr(str,a,name,ar){
  let f = '<span>'+str+'<select name="'+name+'" onchange="'+a+'.value=this.value" required>'+
            '<option value="" hidden></option>';
  ar.forEach(s=> f+='<option value="'+s+'">'+s+'</option>');
  f+='</select></span>';
  return f;
}


let actionStr = {

      begin: function(a){return '<select required name="inputa'+a+'" onchange="actionChangeHandler(event,'+a+');addAddButtons()">'+
                    '<option value="" hidden></option>'+
                    '<option value="everything">everything</option>'+
                    '<option value="device">device is/isn\'t</option>'+
                    '<option value="command">command is/isn\'t</option>'+
                    '<option value="setting">command input is</option>'+
                    '<option value="and">____ and _____</option>'+
                    '<option value="or">____ or _____</option>'+
                    '<option value="not">everything except ____</option>'+
                 '</select>'},
                        
      general: function(a){return '<select required name="inputa'+a+'" onchange="actionChangeHandler(event,'+a+')">'+
                    '<option value="" hidden></option>'+
                    '<option value="device">device is</option>'+
                    '<option value="command">command is</option>'+
                    '<option value="setting">command input is</option>'+
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

actionStr.operators = function(a){
  actionTree[a].name = "operators";
  let name = "inputa"+a;
  return createSelectionStr("","actionTree["+a+"]",name,[" = "," &#x2260; "," &gt; "," &#x2265; "," &lt; "," &#x2264; "]);
};

actionStr.valuechoice = function(a){
  actionTree[a].name = "valuechoice";
  let name = "inputa"+a;
  return "<span><select name='"+name+"' onchange='actionStr.valuechoiceHandler(this,"+a+")' required><option value=''></option><option value='number'>number</option><option value='text'>text</option><option value='boolean'>true/false</option><option value='time'>time</option><option value='date'>date</option></select></span>"; 
};

actionStr.valuechoiceHandler = function(el,a){
  actionTree[a].value1 = el.value;
  el.parentElement.innerHTML = el.value==="boolean"?
    ("<select name='"+el.name+"' onchange='actionTree["+a+"].value2=this.value' required><option value=''></option><option value='true'>true</option><option value=\"false\">false</option></select>"):
    ("<input name='"+el.name+"' type='"+el.value+"' class='numinput' oninput='actionTree["+a+"].value2=this.value' required></span>");
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
  let b1 = actionTree[a].c1 = pushAction();
  let b2 = actionTree[a].c2 = pushAction();
  return "<span>command input "+actionStr.operators(b1)+" "+actionStr.valuechoice(b2)+"</span>";
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


document.getElementById("actionouterspan").innerHTML = actionStr.begin(athead);


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
  athead = pushAction();
  document.getElementById("actionouterspan").innerHTML = actionStr.begin(athead);
  
  let els = document.getElementsByClassName("addAddBtnA");
  while(els.length>0) document.getElementById("actionclause").removeChild(els[0]);
}

actionStr.addJoining = function(inp){
  let a = pushAction();
  actionTree[a].name = inp;
  actionTree[a].c1 = athead;
  let b2 = actionTree[a].c2 = pushAction();
  let outerel = document.getElementById("actionouterspan");
  let form1 = document.getElementById("form1");
  
  let elvalues = [];
  for(let a=0;a<form1.length;a++)
    if(form1[a].name&&form1[a].name.startsWith("inputa")) 
      elvalues.push({name:form1[a].name,value:form1[a].value});
  
  outerel.innerHTML = '<span class="spanbox"><span>'+outerel.innerHTML+'</span> '+inp+' <span>'+actionStr.general(b2)+'</span></span>';
  
  for(let a=0;a<elvalues.length;a++) 
    form1[elvalues[a].name].value = elvalues[a].value;
  
  athead = a;
};


function addAddButtons(){
  if(document.getElementById("form1")["inputa"+athead]&&
     document.getElementById("form1")["inputa"+athead].value === "everything") return;
  
  let b1 = document.createElement("INPUT");
  b1.type = "button"; b1.classList.add("fadeIn"); b1.classList.add("addAddBtnA"); 
  b1.value = 'Add \"and ____\"'; b1.onclick = function(){ actionStr.addJoining("and"); };
  
  let b2 = document.createElement("INPUT");
  b2.type = "button"; b2.classList.add("fadeIn"); b2.classList.add("addAddBtnA"); 
  b2.value = 'Add \"or ____\"'; b2.onclick = function(){ actionStr.addJoining("or"); };
  
  
  document.getElementById("actionclause").appendChild(b1);
  document.getElementById("actionclause").appendChild(b2);
  
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

conditionStr.addJoining = function(elid,inp){
  let a = pushCondition();
  conditionTree[a].name = inp;
  conditionTree[a].c1 = (elid==="onlyifouterspan")?cthead1:cthead2;
  let b2 = conditionTree[a].c2 = pushCondition();
  let outerel = document.getElementById(elid);
  let form1 = document.getElementById("form1");
  
  let elvalues = [];
  for(let a=0;a<form1.length;a++)
    if(form1[a].name&&form1[a].name.startsWith("inputc")) 
      elvalues.push({name:form1[a].name,value:form1[a].value});
  
  outerel.innerHTML = '<span class="spanbox"><span>'+outerel.innerHTML+'</span> '+inp+' <span>'+conditionStr.general(b2)+'</span></span>';
  
  for(let a=0;a<elvalues.length;a++) 
    form1[elvalues[a].name].value = elvalues[a].value;
  
  if(elid==="onlyifouterspan") cthead1 = a;
  else cthead2 = a;
};



function addOnlyIfCondition(){
  document.getElementById("onlyifclause").innerHTML = "Only if <span style='line-height:40px;' id='onlyifouterspan'>"+conditionStr.general(cthead1)+"</span>"+
        "<br><input type='button' value='Clear expression' onclick='addOnlyIfCondition()' class='fadeIn'><input type='button' value='Delete \"only if\"' onclick='deleteOnlyIf()' class='fadeIn'> <input type='button' value='Add \"and ____\"' onclick='conditionStr.addJoining(\"onlyifouterspan\",\"and\")' class='fadeIn'> <input type='button' value='Add \"or ____\"' onclick='conditionStr.addJoining(\"onlyifouterspan\",\"or\")' class='fadeIn'>";
  conditionTree[cthead1] = {name:""};
}

function deleteOnlyIf(){
  document.getElementById("onlyifclause").innerHTML = "<input type=\"button\" value='Add \"Only If\" Clause (optional)' onclick=\"addOnlyIfCondition()\" class=\"fadeIn\">";
  cthead1 = pushCondition();
}

function addExceptCondition(){
  document.getElementById("exceptclause").innerHTML = "Unless <span style='line-height:40px;' id='exceptouterspan'>"+conditionStr.general(cthead2)+"</span>"+
        "<br><input type='button' value='Clear expression' onclick='addExceptCondition()' class='fadeIn'><input type='button' value='Delete \"unless\"' onclick='deleteExcept()' class='fadeIn'> <input type='button' value='Add \"and ____\"' onclick='conditionStr.addJoining(\"exceptouterspan\",\"and\")' class='fadeIn'> <input type='button' value='Add \"or ____\"' onclick='conditionStr.addJoining(\"exceptouterspan\",\"or\")' class='fadeIn'>";
  conditionTree[cthead2] = {name:""};
}

function deleteExcept(){
  document.getElementById("exceptclause").innerHTML = "<input type=\"button\" value='Add \"Unless\" Clause (optional)' onclick=\"addExceptCondition()\" class=\"fadeIn\">";
  cthead2 = pushCondition();
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
    alert("Please enter a valid policy name. Valid policy names must start with a letter, and must only contain numbers, letters, dashes ( - ), and underscores ( _ )");
    e.preventDefault();
    return;
  }
  if(isDuplicateName(form1.policyname.value,currentIndex)){
    //form1.policyname.value = addNumberOnDuplicateName(form1.policyname.value);
    alert("Duplicate name. Please enter a unique policy name.");
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
    if(!(
      isLetter(str.charAt(a))||
      isDigit(str.charAt(a))||
      isAllowedSpecialChar(str.charAt(a))
    ))
      return false;
  return true;
}

function isLetter(c){
  return (c>='a'&&c<='z')||(c>='A'&&c<='Z');
}
function isDigit(c){
  return c>='0'&&c<='9';
}
function isAllowedSpecialChar(c){
  return "-_$#@^&?".indexOf(c)>=0;
}

function isDuplicateName(name,except){
    for(let a=0;a<policies.length;a++)
      if(a!==except&&name===policies[a].policyname) return true;
    return false;
}

function addNumberOnDuplicateName(name){
    let n = name.lastIndexOf("_");
    if(n=== -1) return name+"_1";
    let str = name.substring(n+1);
    let b = true;
    for(let a=0;a<str.length;a++)
      if(!isDigit(str[a])) b = false;
    
    if(b){
      let num = parseInt(str);
      while(isDuplicateName(name.substring(0,n+1)+num,-1)) num++;
      return name.substring(0,n+1)+num;
    }
    else return name+"_1";
    
}








