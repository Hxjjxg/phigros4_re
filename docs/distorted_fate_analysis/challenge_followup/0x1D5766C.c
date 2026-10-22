void RemoveAllSongControl__Awake(RemoveAllSongControl_o *this, const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  System_String_o *String; // x0
  UnityEngine_Object_o *gameObject; // x19
  float v11; // s0
  float v12; // s1
  float v13; // s2

  if ( (byte_446E958 & 1) == 0 ) /*0x1d57688*/
  {
    sub_1B622A0(&UnityEngine_Object_TypeInfo, method); /*0x1d57694*/
    sub_1B622A0(&SaveManagement_TypeInfo, v6); /*0x1d576a0*/
    sub_1B622A0(&StringLiteral_4780, v7); /*0x1d576ac*/
    sub_1B622A0(&StringLiteral_1, v8); /*0x1d576b8*/
    byte_446E958 = 1; /*0x1d576c0*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d576d0*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d576e0*/
  String = SaveManagement__LoadString((System_String_o *)StringLiteral_4780, (System_String_o *)StringLiteral_1, 0); /*0x1d576f0*/
  if ( System_String__op_Equality(String, (System_String_o *)StringLiteral_1, 0) ) /*0x1d576fc*/
  {
    gameObject = (UnityEngine_Object_o *)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)this, 0); /*0x1d5771c*/
    if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1d57720*/
      j_il2cpp_runtime_class_init_0(v11, v12, v13); /*0x1d5772c*/
    UnityEngine_Object__Destroy_63300388(gameObject, 0); /*0x1d57740*/
  }
}
