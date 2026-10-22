void Phigros2_Chapter9_Phase2_Chapter9Phase2Progress__CompleteChapterFromAboutUs(
        Phigros2_Chapter9_Phase2_Chapter9Phase2Progress_o *this,
        const MethodInfo *method)
{
  __int64 v2; // x1
  __int64 v3; // x1
  float v4; // s0
  float v5; // s1
  float v6; // s2
  struct Phigros2_Chapter9_Phase2_Chapter9Phase2Session_StaticFields *static_fields; // x8

  if ( (byte_446E76C & 1) == 0 ) /*0x1d1c210*/
  {
    sub_1B622A0(&UnityEngine_Debug_TypeInfo, method); /*0x1d1c21c*/
    this = (Phigros2_Chapter9_Phase2_Chapter9Phase2Progress_o *)sub_1B622A0(&"Tried to complete Chapter 9 outside the valid credits state.", v2); /*0x1d1c228*/
    byte_446E76C = 1; /*0x1d1c230*/
  }
  if ( Phigros2_Chapter9_Phase2_Chapter9Phase2Progress__LoadCurrentStep((const MethodInfo *)this) != 4 ) /*0x1d1c23c*/
    goto LABEL_15; /*0x1d1c23c*/
  if ( !byte_446E5E7 ) /*0x1d1c248*/
  {
    sub_1B622A0(&Phigros2_Chapter9_Phase2_Chapter9Phase2Session_TypeInfo, v3); /*0x1d1c254*/
    byte_446E5E7 = 1; /*0x1d1c25c*/
  }
  static_fields = Phigros2_Chapter9_Phase2_Chapter9Phase2Session_TypeInfo->static_fields; /*0x1d1c26c*/
  if ( !static_fields->_IsActive_k__BackingField ) /*0x1d1c270*/
    goto LABEL_15; /*0x1d1c270*/
  if ( !byte_446E5E8 ) /*0x1d1c280*/
  {
    sub_1B622A0(&Phigros2_Chapter9_Phase2_Chapter9Phase2Session_TypeInfo, v3); /*0x1d1c288*/
    byte_446E5E8 = 1; /*0x1d1c294*/
    static_fields = Phigros2_Chapter9_Phase2_Chapter9Phase2Session_TypeInfo->static_fields; /*0x1d1c298*/
  }
  if ( static_fields->_Stage_k__BackingField != 7 ) /*0x1d1c2a4*/
  {
LABEL_15:
    if ( !UnityEngine_Debug_TypeInfo->_2.cctor_finished ) /*0x1d1c2c4*/
      j_il2cpp_runtime_class_init_0(v4, v5, v6); /*0x1d1c2d0*/
    UnityEngine_Debug__LogError((Il2CppObject *)"Tried to complete Chapter 9 outside the valid credits state.", 0); /*0x1d1c2e4*/
  }
}
