package dev.lonami.uniffidl;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import dev.lonami.uniffidl.psi.UdlTypes;
import com.intellij.psi.TokenType;

%%

%class UdlLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}

EMPTY = ""

// Most of UDL comes from WebIDL's grammar at https://webidl.spec.whatwg.org/#idl-grammar

integer    = -?([1-9][0-9]*|0[Xx][0-9A-Fa-f]+|0[0-7]*)
decimal    = -?(([0-9]+\.[0-9]*|[0-9]*\.[0-9]+)([Ee][+-]?[0-9]+)?|[0-9]+[Ee][+-]?[0-9]+)
identifier = [_-]?[A-Za-z][0-9A-Z_a-z-]*
string     = \"[^\"]*\"
whitespace = [\t\n\r ]+
comment    = \/\/.*|\/\*[^]*?\*\/
other      = [^\t\n\r 0-9A-Za-z]
nothing    = ""

Definitions =
    {ExtendedAttributeList} {Definition} {Definitions}
    | {EMPTY}

Definition =
    {CallbackOrInterfaceOrMixin}
    | {Namespace}
    | {Partial}
    | {Dictionary}
    | {Enum}
    | {Typedef}
    | {IncludesStatement}

ArgumentNameKeyword =
    "async"
    | "attribute"
    | "callback"
    | "const"
    | "constructor"
    | "deleter"
    | "dictionary"
    | "enum"
    | "getter"
    | "includes"
    | "inherit"
    | "interface"
    | "iterable"
    | "maplike"
    | "mixin"
    | "namespace"
    | "partial"
    | "readonly"
    | "required"
    | "setlike"
    | "setter"
    | "static"
    | "stringifier"
    | "typedef"
    | "unrestricted"

CallbackOrInterfaceOrMixin =
    "callback" {CallbackRestOrInterface}
    | "interface" {InterfaceOrMixin}

InterfaceOrMixin =
    {InterfaceRest}
    | {MixinRest}

InterfaceRest =
    {identifier} {Inheritance} "{" {InterfaceMembers} "}" ";"

Partial =
    "partial" {PartialDefinition}

PartialDefinition =
    "interface" {PartialInterfaceOrPartialMixin}
    | {PartialDictionary}
    | {Namespace}

PartialInterfaceOrPartialMixin =
    {PartialInterfaceRest}
    | {MixinRest}

PartialInterfaceRest =
    {identifier} "{" {PartialInterfaceMembers} "}" ";"

InterfaceMembers =
    {ExtendedAttributeList} {InterfaceMember} {InterfaceMembers}
    | {EMPTY}

InterfaceMember =
    {PartialInterfaceMember}
    | {Constructor}

PartialInterfaceMembers =
    {ExtendedAttributeList} {PartialInterfaceMember} {PartialInterfaceMembers}
    | {EMPTY}

PartialInterfaceMember =
    {Const}
    | {Operation}
    | {Stringifier}
    | {StaticMember}
    | {Iterable}
    | {AsyncIterable}
    | {ReadOnlyMember}
    | {ReadWriteAttribute}
    | {ReadWriteMaplike}
    | {ReadWriteSetlike}
    | {InheritAttribute}

Inheritance =
    ":" {identifier}
    | {EMPTY}

MixinRest =
    "mixin" {identifier} "{" {MixinMembers} "}" ";"

MixinMembers =
    {ExtendedAttributeList} {MixinMember} {MixinMembers}
    | {EMPTY}

MixinMember =
    {Const}
    | {RegularOperation}
    | {Stringifier}
    | {OptionalReadOnly} {AttributeRest}

IncludesStatement =
    {identifier} "includes" {identifier} ";"

CallbackRestOrInterface =
    {CallbackRest}
    | "interface" {identifier} "{" {CallbackInterfaceMembers} "}" ";"

CallbackInterfaceMembers =
    {ExtendedAttributeList} {CallbackInterfaceMember} {CallbackInterfaceMembers}
    | {EMPTY}

CallbackInterfaceMember =
    {Const}
    | {RegularOperation}

Const =
    "const" {ConstType} {identifier} "=" {ConstValue} ";"

ConstValue =
    {BooleanLiteral}
    | {FloatLiteral}
    | {integer}

BooleanLiteral =
    "true"
    | "false"

FloatLiteral =
    {decimal}
    | "-Infinity"
    | "Infinity"
    | "NaN"

ConstType =
    {PrimitiveType}
    | {identifier}

ReadOnlyMember =
    "readonly" {ReadOnlyMemberRest}

ReadOnlyMemberRest =
    {AttributeRest}
    | {MaplikeRest}
    | {SetlikeRest}

ReadWriteAttribute =
    {AttributeRest}

InheritAttribute =
    "inherit" {AttributeRest}

AttributeRest =
    "attribute" {TypeWithExtendedAttributes} {AttributeName} ";"

AttributeName =
    {AttributeNameKeyword}
    | {identifier}

AttributeNameKeyword =
    "async"
    | "required"

OptionalReadOnly =
    "readonly"
    | {EMPTY}

DefaultValue =
    {ConstValue}
    | "string"
    | "[ ]"
    | "{ }"
    | "null"
    | "undefined"

Operation =
    {RegularOperation}
    | {SpecialOperation}

RegularOperation =
    {Type} {OperationRest}

SpecialOperation =
    {Special} {RegularOperation}

Special =
    "getter"
    | "setter"
    | "deleter"

OperationRest =
    {OptionalOperationName} "(" {ArgumentList} ")" ";"

OptionalOperationName =
    {OperationName}
    | {EMPTY}

OperationName =
    {OperationNameKeyword}
    | {identifier}

OperationNameKeyword =
    "includes"

ArgumentList =
    {Argument} {Arguments}
    | {EMPTY}

Arguments =
    "," {Argument} {Arguments}
    | {EMPTY}

Argument =
    {ExtendedAttributeList} {ArgumentRest}

ArgumentRest =
    "optional" {TypeWithExtendedAttributes} {ArgumentName} {Default}
    | {Type} {Ellipsis} {ArgumentName}

ArgumentName =
    {ArgumentNameKeyword}
    | {identifier}

Ellipsis =
    "..."
    | {EMPTY}

Constructor =
    "constructor" "(" {ArgumentList} ")" ";"

Stringifier =
    "stringifier" {StringifierRest}

StringifierRest =
    {OptionalReadOnly} {AttributeRest}
    | ";"

StaticMember =
    "static" {StaticMemberRest}

StaticMemberRest =
    {OptionalReadOnly} {AttributeRest}
    | {RegularOperation}

Iterable =
    "iterable" "<" {TypeWithExtendedAttributes} {OptionalType} ">" ";"

OptionalType =
    "," {TypeWithExtendedAttributes}
    | {EMPTY}

AsyncIterable =
    "async" "iterable" "<" {TypeWithExtendedAttributes} {OptionalType} ">" {OptionalArgumentList} ";"

OptionalArgumentList =
    "(" {ArgumentList} ")"
    | {EMPTY}

ReadWriteMaplike =
    {MaplikeRest}

MaplikeRest =
    "maplike" "<" {TypeWithExtendedAttributes} "," {TypeWithExtendedAttributes} ">" ";"

ReadWriteSetlike =
    {SetlikeRest}

SetlikeRest =
    "setlike" "<" {TypeWithExtendedAttributes} ">" ";"

Namespace =
    "namespace" {identifier} "{" {NamespaceMembers} "}" ";"

NamespaceMembers =
    {ExtendedAttributeList} {NamespaceMember} {NamespaceMembers}
    | {EMPTY}

NamespaceMember =
    {RegularOperation}
    | "readonly" {AttributeRest}
    | {Const}

Dictionary =
    "dictionary" "identifier" {Inheritance} "{" {DictionaryMembers} "}" ";"

DictionaryMembers =
    {DictionaryMember} {DictionaryMembers}
    | {EMPTY}

DictionaryMember =
    {ExtendedAttributeList} {DictionaryMemberRest}

DictionaryMemberRest =
    "required" {TypeWithExtendedAttributes} {identifier} ";"
    | {Type} {identifier} {Default} ";"

PartialDictionary =
    "dictionary" {identifier} "{" {DictionaryMembers} "}" ";"

Default =
    "=" {DefaultValue}
    | {EMPTY}

Enum =
    "enum" {identifier} "{" {EnumValueList} "}" ";"

EnumValueList =
    "string" {EnumValueListComma}

EnumValueListComma =
    "," {EnumValueListString}
    | {EMPTY}

EnumValueListString =
    "string" {EnumValueListComma}
    | {EMPTY}

CallbackRest =
    {identifier} "=" {Type} "(" {ArgumentList} ")" ";"

Typedef =
    "typedef" {TypeWithExtendedAttributes} {identifier} ";"

Type =
    {SingleType}
    | {UnionType} {Null}

TypeWithExtendedAttributes =
    {ExtendedAttributeList} {Type}

SingleType =
    {DistinguishableType}
    | "any"
    | {PromiseType}

UnionType =
    "(" {UnionMemberType} "or" {UnionMemberType} {UnionMemberTypes} ")"

UnionMemberType =
    {ExtendedAttributeList} {DistinguishableType}
    | {UnionType} {Null}

UnionMemberTypes =
    "or" {UnionMemberType} {UnionMemberTypes}
    | {EMPTY}

DistinguishableType =
    {PrimitiveType} {Null}
    | {StringType} {Null}
    | {identifier} {Null}
    | "sequence" "<" {TypeWithExtendedAttributes} ">" {Null}
    | "object" {Null}
    | "symbol" {Null}
    | {BufferRelatedType} {Null}
    | "FrozenArray" "<" {TypeWithExtendedAttributes} ">" {Null}
    | "ObservableArray" "<" {TypeWithExtendedAttributes} ">" {Null}
    | {RecordType} {Null}
    | "undefined" {Null}

PrimitiveType =
    {UnsignedIntegerType}
    | {UnrestrictedFloatType}
    | "boolean"
    | "byte"
    | "octet"
    | "bigint"

UnrestrictedFloatType =
    "unrestricted" {FloatType}
    | {FloatType}

FloatType =
    "float"
    | "double"

UnsignedIntegerType =
    "unsigned" {IntegerType}
    | {IntegerType}

IntegerType =
    "short"
    | "long" {OptionalLong}

OptionalLong =
    "long"
    | {EMPTY}

StringType =
    "ByteString"
    | "DOMString"
    | "USVString"

PromiseType =
    "Promise" "<" {Type} ">"

RecordType =
    "record" "<" {StringType} "," {TypeWithExtendedAttributes} ">"

Null =
    "?"
    | {EMPTY}

BufferRelatedType =
    "ArrayBuffer"
    | "DataView"
    | "Int8Array"
    | "Int16Array"
    | "Int32Array"
    | "Uint8Array"
    | "Uint16Array"
    | "Uint32Array"
    | "Uint8ClampedArray"
    | "BigInt64Array"
    | "BigUint64Array"
    | "Float32Array"
    | "Float64Array"

ExtendedAttributeList =
    "[" {ExtendedAttribute} {ExtendedAttributes} "]"
    | {EMPTY}

ExtendedAttributes =
    "," {ExtendedAttribute} {ExtendedAttributes}
    | {EMPTY}

ExtendedAttribute =
    "(" {ExtendedAttributeInner} ")" {ExtendedAttributeRest}
    | "[" {ExtendedAttributeInner} "]" {ExtendedAttributeRest}
    | "{" {ExtendedAttributeInner} "}" {ExtendedAttributeRest}
    | {Other} {ExtendedAttributeRest}

ExtendedAttributeRest =
    {ExtendedAttribute}
    | {EMPTY}

ExtendedAttributeInner =
    "(" {ExtendedAttributeInner} ")" {ExtendedAttributeInner}
    | "[" {ExtendedAttributeInner} "]" {ExtendedAttributeInner}
    | "|" "{" {ExtendedAttributeInner} "}" {ExtendedAttributeInner}
    | "|" {OtherOrComma} {ExtendedAttributeInner}
    | {EMPTY}

Other =
    {integer}
    | {decimal}
    | {identifier}
    | {string}
    | {other}
    | "-"
    | "-Infinity"
    | "."
    | "..."
    | ":"
    | ";"
    | "<"
    | "="
    | ">"
    | "?"
    | "*"
    | "ByteString"
    | "DOMString"
    | "FrozenArray"
    | "Infinity"
    | "NaN"
    | "ObservableArray"
    | "Promise"
    | "USVString"
    | "any"
    | "bigint"
    | "boolean"
    | "byte"
    | "double"
    | "false"
    | "float"
    | "long"
    | "null"
    | "object"
    | "octet"
    | "or"
    | "optional"
    | "record"
    | "sequence"
    | "short"
    | "symbol"
    | "true"
    | "unsigned"
    | "undefined"
    | {ArgumentNameKeyword}
    | {BufferRelatedType}

OtherOrComma =
    {Other}
    | ","

IdentifierList =
    {identifier} {Identifiers}

Identifiers =
    "," {identifier} {Identifiers}
    | {EMPTY}

ExtendedAttributeNoArgs =
    {identifier}

ExtendedAttributeArgList =
    {identifier} "(" {ArgumentList} ")"

ExtendedAttributeIdent =
    {identifier} "=" {identifier}

ExtendedAttributeWildcard =
    {identifier} "=" "*"

ExtendedAttributeIdentList =
    {identifier} = "(" {IdentifierList} ")"

ExtendedAttributeNamedArgList =
    {identifier} "=" {identifier} "(" {ArgumentList} ")"

%%

[^]                                                         { return TokenType.BAD_CHARACTER; }
