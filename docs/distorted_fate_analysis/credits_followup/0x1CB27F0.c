void AboutUs__StartPlay(AboutUs_o *this, const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  System_String_o *String; // x0
  float v10; // s0
  float v11; // s1
  float v12; // s2
  __int64 v13; // x1
  UnityEngine_GameObject_o *cutOut; // x0

  if ( (byte_446E31C & 1) == 0 ) /*0x1cb2810*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1cb281c*/
    sub_1B622A0(&"4.0", v6); /*0x1cb2828*/
    sub_1B622A0(&"GameCompleted", v7); /*0x1cb2834*/
    sub_1B622A0(&"", v8); /*0x1cb2840*/
    byte_446E31C = 1; /*0x1cb2848*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cb285c*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cb2870*/
  String = SaveManagement__LoadString((System_String_o *)"GameCompleted", (System_String_o *)"", 0); /*0x1cb2880*/
  if ( System_String__op_Inequality(String, (System_String_o *)"4.0", 0) ) /*0x1cb288c*/
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cb2898*/
      j_il2cpp_runtime_class_init_0(v10, v11, v12); /*0x1cb28a0*/
    SaveManagement__SaveString((System_String_o *)"GameCompleted", (System_String_o *)"4.0", 0); /*0x1cb28b0*/
  }
  if ( UnityEngine_Time__get_timeSinceLevelLoad(0) >= 1.0 ) /*0x1cb28c4*/
  {
    cutOut = this->fields.cutOut; /*0x1cb28d8*/
    this->fields.playing = 1; /*0x1cb28e0*/
    if ( !cutOut ) /*0x1cb28e4*/
      sub_1B624F8(0, v13); /*0x1cb2900*/
    UnityEngine_GameObject__SetActive(cutOut, 1, 0); /*0x1cb28fc*/
  }
}
