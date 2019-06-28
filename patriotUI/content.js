


const policies = [];

let currentIndex = -1;
let actionTree = [{name:""}];
let conditionTree = [{name:""},{name:""}];
//during an edit, index will refer to policies list,
//actionTree and conditionTree will both point to that object



const actionArrays = {
  devices:["door1","door2","door3","door4"],
  commands:["on","off","open","close"],
  settings:["s1","s2","s3","s4"]
};

const conditionArrays = {
  devices:["FireSprinkler","WaterLeakSensor","VacationMode","MyPresence","HallwayMotionSensor"],
  states:["detected","not detected","on","off"],
  automationunits:["garage-door-opener","energy-saver","flood-alert","ready-for-rain"]
};






function editPolicy(el){
  openPolicyEditingForm(getInd(el));
}
function deletePolicy(el){
  let warning = "Are you sure you want to delete this policy?\n\n";
  warning+=el.children[0].children[0].children[0].innerText;
  if(!confirm(warning)) return;
  
  policies.splice(getInd(el),1);
  document.getElementById("menusectionpolicylist").removeChild(el);
}

function newPolicy(){
  let newEl = document.createElement("DIV");
  newEl.classList.add("policyContainer");
  newEl.innerHTML = '<div class="card bg-info text-white"><div class="card-body"><div style="float:left">[loading policy text...]</div><div style="float:right"><button class="btn btn-warning" onclick="editPolicy(this.parentElement.parentElement.parentElement.parentElement)">Edit</button> <button class="btn btn-warning" onclick="deletePolicy(this.parentElement.parentElement.parentElement.parentElement)">Delete</button></div></div></div><br>';
  document.getElementById("menusectionpolicylist").append(newEl);
  
  policies.push({
    text:"",
    policyname:"",
    allowdeny:0,
    actionTree:[{name:""}],
    conditionTree:[{name:""},{name:""}]
  });
  editPolicy(newEl);
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
  let str = "POLICY "+form1.policyname.value+":\n";
  let actionCode = constructActionCode(0);
  str+=form1.allowdeny.value+" "+(actionCode===""?"EVERYTHING":actionCode)+"\n";
  if(conditionTree[0].name!=="") 
    str+="ONLY IF "+constructConditionCode(0)+"\n";
  if(conditionTree[1].name!=="")
    str+="EXCEPT "+constructConditionCode(1);
  
  policies[currentIndex] = {
    text:str,
    policyname:form1.policyname.value,
    allowdeny:form1.allowdeny.selectedIndex,
    actionTree:actionTree,
    conditionTree:conditionTree
  };
  document.getElementsByClassName("policyContainer")[currentIndex].children[0].children[0].children[0].innerText = str;
}

function openPolicyEditingForm(ind){
  document.getElementById("menusection").style.display = "none";
  document.getElementById("buildersection").style.display = "block";
  
  //reconstruct form 
  let form1 = document.getElementById("form1");
  form1.policyname.value = policies[ind].policyname;
  form1.allowdeny.selectedIndex = policies[ind].allowdeny;
  
  
  
  
  currentIndex = ind;
}


function closePolicyEditingForm(){
  document.getElementById("buildersection").style.display = "none";
  document.getElementById("menusection").style.display = "block";
  currentIndex = -1;
  actionTree = [{name:""}];
  conditionTree = [{name:""},{name:""}];
  document.getElementById("form1").policyname.value = "";
  document.getElementById("form1").allowdeny.selectedIndex = 0;
  clearAction();
  deleteOnlyIf();
  deleteExcept();
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
  switch(node.name){
    case "isisnot": return node.value;
    case "device": return "action_device"+actionTree[node.c].value+node.value;
    case "command": return "action_command"+actionTree[node.c].value+node.value;
    case "setting": return "action_command_arg"+actionTree[node.c].value+node.value;
    case "and": return "("+constructActionCode(node.c1)+") AND ("+constructActionCode(node.c2)+")";
    case "or": return "("+constructActionCode(node.c1)+") OR ("+constructActionCode(node.c2)+")";
    case "not": return "NOT ("+constructActionCode(node.c)+")";
    default: return "";
  }
}

function constructConditionCode(a){ //a=0, only if. a=1, except.
  let node = conditionTree[a];
  switch(conditionTree[a].name){
    case "isisnot": return node.value;
    case "device": return node.value;
    case "state": return node.value;
    case "within": return "WITHIN ["+node.value1+", "+node.value2+"]";
    case "operators": return node.value;
    case "automationunit": return "automation_unit"+conditionTree[node.c].value+node.value;
    case "devicestate": return "state("+conditionTree[node.c1].value+")"+conditionTree[node.c2].value+conditionTree[node.c3].value;
    case "devicevalue": return "state("+conditionTree[node.c1].value+")"+conditionTree[node.c2].value+node.value;
    case "time": return "current_time"+conditionTree[node.c].value+node.value+":00";
    case "date": return "current_date"+conditionTree[node.c].value+invertDate(node.value);
    case "since": return "SINCE("+constructConditionCode(node.c1)+", "+constructConditionCode(node.c2)+") "+constructConditionCode(node.c3);
    case "once": return "ONCE("+constructConditionCode(node.c1)+") "+constructConditionCode(node.c2);
    case "lastly": return "LASTLY("+constructConditionCode(node.c1)+") "+constructConditionCode(node.c2);
    case "and": return "("+constructConditionCode(node.c1)+") AND ("+constructConditionCode(node.c2)+")";
    case "or": return "("+constructConditionCode(node.c1)+") OR ("+constructConditionCode(node.c2)+")";
    case "not": return "NOT ("+constructConditionCode(node.c)+")";
    default: return "";
  }
}

function invertDate(d){
  let ar = d.split("-");
  return ar[1]+"-"+ar[2]+"-"+ar[0];
}









function createSelectionStr(str,a,ar){
  let f = '<span>'+str+'<select onchange="'+a+'.value=this.value" required>'+
            '<option value=""></option>';
  ar.forEach(s=> f+='<option value="'+s+'">'+s+'</option>');
  f+='</select></span>';
  return f;
}


let actionStr = {

      begin: '<select required onchange="actionChangeHandler(event,0)">'+
                    '<option value=""></option>'+
                    '<option value="everything">everything</option>'+
                    '<option value="device">device is/isn\'t</option>'+
                    '<option value="command">command is/isn\'t</option>'+
                    '<option value="setting">setting is/isn\'t</option>'+
                    '<option value="and">____ and _____</option>'+
                    '<option value="or">____ or _____</option>'+
                    '<option value="not">not ____ </option>'+
                 '</select>',
                        
      general: function(a){return '<select required onchange="actionChangeHandler(event,'+a+')">'+
                    '<option value=""></option>'+
                    '<option value="device">device is</option>'+
                    '<option value="command">command is</option>'+
                    '<option value="setting">setting is</option>'+
                    '<option value="and">____ and _____</option>'+
                    '<option value="or">____ or _____</option>'+
                    '<option value="not">not ____ </option>'+
                 '</select>'}
};
                  
actionStr.isisnot = function(a){
  actionTree[a].name = "isisnot";
  actionTree[a].value = " = ";
  return " <select onchange='actionTree["+a+"].value=this.value'><option value=' = '>is</option><option value=' != '>is not</option></select> ";
};
actionStr.device  = function(a){
  actionTree[a].name = "device";
  let b = actionTree[a].c = pushAction();
  return createSelectionStr("device"+actionStr.isisnot(b),"actionTree["+a+"]",actionArrays.devices);
};
actionStr.command = function(a){
  actionTree[a].name = "command";
  let b = actionTree[a].c = pushAction();
  return createSelectionStr("command"+actionStr.isisnot(b),"actionTree["+a+"]",actionArrays.commands);
};
actionStr.setting = function(a){
  actionTree[a].name = "setting";
  let b = actionTree[a].c = pushAction();
  return createSelectionStr("setting"+actionStr.isisnot(b),"actionTree["+a+"]",actionArrays.settings);
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
  return '<span class="spanbox"> not <span>'+actionStr.general(b)+'</span></span>';
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
    general: function(a){return '<select required onchange="conditionChangeHandler(event,'+a+')">'+
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
       '<option value="not">not ____ </option>'+
    '</select>';}
};

conditionStr.isisnot = function(a){
  conditionTree[a].name = "isisnot";
  conditionTree[a].value = " = ";
  return " <select onchange='conditionTree["+a+"].value=this.value'><option value=' = '>is</option><option value=' != '>is not</option></select> ";
};
conditionStr.device = function(a){
  conditionTree[a].name = "device";
  return createSelectionStr("","conditionTree["+a+"]",conditionArrays.devices);
};
conditionStr.state = function(a){
  conditionTree[a].name = "state";
  return createSelectionStr("","conditionTree["+a+"]",conditionArrays.states);
};
conditionStr.within = function(a){
  conditionTree[a].name = "within";
  return " between <input type='number' class='numinput' oninput='conditionTree["+a+"].value1=this.value' required> "+
         "and <input type='number' class='numinput' oninput='conditionTree["+a+"].value2=this.value' required> seconds ago";
};
conditionStr.operators = function(a){
  conditionTree[a].name = "operators";
  return createSelectionStr("","conditionTree["+a+"]",[" = "," &#x2260; "," &gt; "," &#x2265; "," &lt; "," &#x2264; "]);
};
conditionStr.automationunit = function(a){
  conditionTree[a].name = "automationunit";
  let b = conditionTree[a].c = pushCondition();
  return createSelectionStr("automation unit"+conditionStr.isisnot(b),"conditionTree["+a+"]",conditionArrays.automationunits);
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
  return '<span>Value of <span>'+conditionStr.device(b1)+'</span> '+conditionStr.operators(b2)+
      " <input type='number' class='numinput' oninput='conditionTree["+a+"].value=this.value' required></span>";
};
conditionStr.time = function(a){
  conditionTree[a].name = "time";
  let b = conditionTree[a].c = pushCondition();
  return "<span>Time "+conditionStr.operators(b)+
        " <input type='time' class='numinput' oninput='conditionTree["+a+"].value=this.value' required></span>";
};
conditionStr.date = function(a){
  conditionTree[a].name = "date";
  let b = conditionTree[a].c = pushCondition();
  return "<span>Date "+conditionStr.operators(b)+
        " <input type='date' class='numinput' oninput='conditionTree["+a+"].value=this.value' required></span>";
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
  return '<span class="spanbox"> not <span>'+conditionStr.general(b)+'</span></span>';
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














