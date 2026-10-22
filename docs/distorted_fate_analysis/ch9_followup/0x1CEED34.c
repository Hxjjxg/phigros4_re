void Phigros2_Chapter9_C9S6Unlock__Complete(const MethodInfo *method)
{
  __int64 v1; // x1
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v5; // x1

  if ( (byte_446E5C8 & 1) == 0 ) /*0x1ceed4c*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, v1); /*0x1ceed58*/
    sub_1B622A0(&StringLiteral_2581, v5); /*0x1ceed64*/
    byte_446E5C8 = 1; /*0x1ceed6c*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1ceed78*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1ceed84*/
  SaveManagement__SaveBool((System_String_o *)StringLiteral_2581, 1, 0); /*0x1ceed9c*/
}
