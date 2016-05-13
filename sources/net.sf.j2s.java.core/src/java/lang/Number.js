Clazz.load (["java.io.Serializable"], "java.lang.Number", null, function () {
java.lang.Number = Number;
Number.prototype.to$tring = Number.prototype.toString;
(function() {
	if (Clazz.supportsNativeObject) {
		for (var i = 0; i < Clazz.extendedObjectMethods.length; i++) {
			var p = Clazz.extendedObjectMethods[i];
			if (p == "toString" || p == "to$tring") continue;
			Number.prototype[p] = JavaObject.prototype[p];
		}
	}
}) ();
//Clazz.decorateAsType (Number, "Number", null, java.io.Serializable, null, true);
Number.__CLASS_NAME__ = "Number";
Clazz.implementOf (Number, java.io.Serializable);
Number.equals = Clazz.innerFunctions.equals;
Number.getName = Clazz.innerFunctions.getName;

Number.serialVersionUID = Number.prototype.serialVersionUID = -8742448824652078965;

Clazz.defineMethod (Number, "shortValue", 
function () {
return Math.round (this) & 0xffff;
});

Clazz.defineMethod (Number, "byteValue", 
function () {
return Math.round (this) & 0xff;
});

Clazz.defineMethod (Number, "intValue", 
function () {
return Math.round (this) & 0xffffffff;
});

Clazz.defineMethod (Number, "longValue", 
function () {
return Math.round (this);
});

Clazz.defineMethod (Number, "floatValue", 
function () {
return this.valueOf ();
});

Clazz.defineMethod (Number, "doubleValue", 
function () {
return this.valueOf ();
});

//sgurin : added this because if not, a native number in native code will print as [Object Number] instead printing the number value... 
Clazz.overrideMethod (Number, "toString", 
function () {
	if (arguments.length > 0) {
		return Number.prototype.to$tring.apply (this, arguments);
	} else {
		return this.valueOf () + "";
	}
});

Clazz.overrideMethod (Number, "hashCode", 
function () {
return this.valueOf ();
});
});
