bool Phigros2_Chapter9_ExoplanetaryMirageUnlock__IsProcessActive(const MethodInfo *method)
{
  __int64 v1; // x1
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  float v8; // s0
  float v9; // s1
  float v10; // s2
  const MethodInfo *method_1; // x1

  if ( (byte_446E6AB & 1) == 0 ) /*0x1d0a514*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, v1); /*0x1d0a520*/
    sub_1B622A0(&"poyidomejiu1", v5); /*0x1d0a52c*/
    sub_1B622A0(&"C9ExoplanetaryMirageUnlocked", v6); /*0x1d0a538*/
    sub_1B622A0(&"Chapter9UnlockBegin", v7); /*0x1d0a544*/
    byte_446E6AB = 1; /*0x1d0a54c*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d0a558*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d0a564*/
  if ( !SaveManagement__LoadBool((System_String_o *)"Chapter9UnlockBegin", 0, 0) ) /*0x1d0a578*/
    return 0; /*0x1d0a578*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d0a584*/
    j_il2cpp_runtime_class_init_0(v8, v9, v10); /*0x1d0a590*/
  return !SaveManagement__LoadBool((System_String_o *)"C9ExoplanetaryMirageUnlocked", 0, 0) /*0x1d0a5cd*/
      && Phigros2_Chapter9_Key__IsCollectionRead((System_String_o *)"poyidomejiu1", method_1);
}
