﻿$_L(["$wt.dnd.DragUnderEffect"],"$wt.dnd.TreeDragUnderEffect",null,function(){
c$=$_C(function(){
this.tree=null;
this.dropIndex=0;
this.scrollIndex=0;
this.scrollBeginTime=0;
this.expandIndex=0;
this.expandBeginTime=0;
this.clearInsert=false;
$_Z(this,arguments);
},$wt.dnd,"TreeDragUnderEffect",$wt.dnd.DragUnderEffect);
$_K(c$,
function(tree){
$_R(this,$wt.dnd.TreeDragUnderEffect,[]);
this.tree=tree;
},"$wt.widgets.Tree");
$_M(c$,"checkEffect",
($fz=function(effect){
if((effect&1)!=0)effect=effect&-5&-3;
if((effect&2)!=0)effect=effect&-5;
return effect;
},$fz.isPrivate=true,$fz),"~N");
$_V(c$,"show",
function(effect,x,y){
effect=this.checkEffect(effect);
},"~N,~N,~N");
$_S(c$,
"SCROLL_HYSTERESIS",150,
"EXPAND_HYSTERESIS",300);
});