void Phigros2_Chapter9_Chapter9Phase1Control__OpenInputPassword(
        Phigros2_Chapter9_Chapter9Phase1Control_o *this,
        const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v6; // x1
  __int64 v7; // x1
  UnityEngine_Behaviour_o *worldCanvasScroller; // x0
  const MethodInfo *method_1; // x2
  const MethodInfo *method_2; // x1
  System_Collections_IEnumerator_o *routine; // x1
  const MethodInfo *method_3; // x2

  if ( (byte_446E61F & 1) == 0 ) /*0x1cfca3c*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1cfca48*/
    sub_1B622A0(&StringLiteral_2573, v6); /*0x1cfca54*/
    byte_446E61F = 1; /*0x1cfca5c*/
  }
  if ( !this->fields.onPlay && !this->fields.onAnim && !this->fields.mapFocusFollow ) /*0x1cfca70*/
  {
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cfca84*/
      j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cfca8c*/
    if ( SaveManagement__LoadBool((System_String_o *)StringLiteral_2573, 0, 0) ) /*0x1cfcaa4*/
    {
      worldCanvasScroller = (UnityEngine_Behaviour_o *)this->fields.worldCanvasScroller; /*0x1cfcaac*/
      this->fields.isInputPasswordOpened = 1; /*0x1cfcab4*/
      if ( !worldCanvasScroller /*0x1cfcae8*/
        || (UnityEngine_Behaviour__set_enabled(worldCanvasScroller, 0, 0),
            Phigros2_Chapter9_Chapter9Phase1Control__PrepareBlurBackground(this, 1, method_1),
            (worldCanvasScroller = (UnityEngine_Behaviour_o *)this->fields.inputPasswordControl) == 0)
        || (Chapter9InputPasswordControl__Open((Chapter9InputPasswordControl_o *)worldCanvasScroller, 0),
            (worldCanvasScroller = (UnityEngine_Behaviour_o *)this->fields.openInputPasswordSE) == 0) )
      {
        sub_1B624F8(worldCanvasScroller, v7); /*0x1cfcb1c*/
      }
      UnityEngine_AudioSource__Play_62985964((UnityEngine_AudioSource_o *)worldCanvasScroller, 0); /*0x1cfcaf0*/
      routine = Phigros2_Chapter9_Chapter9Phase1Control__DuckBgm(this, method_2); /*0x1cfcafc*/
      Phigros2_Chapter9_Chapter9Phase1Control__StartBgmTransition(this, routine, method_3); /*0x1cfcb0c*/
    }
  }
}
