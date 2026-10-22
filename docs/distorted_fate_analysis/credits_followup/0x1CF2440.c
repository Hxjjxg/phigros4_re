bool Phigros2_Chapter9_C9DeductionControl__FinishAndWhiteOut_d__76__MoveNext(
        Phigros2_Chapter9_C9DeductionControl__FinishAndWhiteOut_d__76_o *this,
        const MethodInfo *method)
{
  const MethodInfo *method_1; // x3
  float v3; // s0
  float v4; // s1
  float v5; // s2
  __int64 v7; // x1
  int32_t _1__state; // w8
  struct Phigros2_Chapter9_C9DeductionControl_o *_4__this; // x0
  float inDuration; // s2
  UnityEngine_AnimationCurve_o *inCurve; // x1
  bool v12; // w20

  if ( (byte_446E5BF & 1) == 0 ) /*0x1cf2454*/
  {
    sub_1B622A0(&UnityEngine_SceneManagement_SceneManager_TypeInfo, method); /*0x1cf2460*/
    sub_1B622A0(&"AfterDeduction", v7); /*0x1cf246c*/
    byte_446E5BF = 1; /*0x1cf2474*/
  }
  _1__state = this->fields.__1__state; /*0x1cf2478*/
  if ( _1__state == 1 ) /*0x1cf2480*/
  {
    this->fields.__1__state = -1; /*0x1cf24c8*/
    if ( !UnityEngine_SceneManagement_SceneManager_TypeInfo->_2.cctor_finished ) /*0x1cf24d8*/
      j_il2cpp_runtime_class_init_0(v3, v4, v5); /*0x1cf24e0*/
    UnityEngine_SceneManagement_SceneManager__LoadScene((System_String_o *)"AfterDeduction", 0); /*0x1cf24f4*/
    return 0; /*0x1cf24f4*/
  }
  if ( _1__state ) /*0x1cf2484*/
    return 0; /*0x1cf24f8*/
  _4__this = this->fields.__4__this; /*0x1cf2488*/
  this->fields.__1__state = -1; /*0x1cf2490*/
  if ( !_4__this ) /*0x1cf2494*/
    sub_1B624F8(0, method); /*0x1cf250c*/
  inDuration = _4__this->fields.inDuration; /*0x1cf2498*/
  inCurve = _4__this->fields.inCurve; /*0x1cf249c*/
  v12 = 1; /*0x1cf24a0*/
  _4__this->fields.isAnimating = 1; /*0x1cf24b0*/
  this->fields.__2__current = (Il2CppObject *)Phigros2_Chapter9_C9DeductionControl__TweenLevels( /*0x1cf24b8*/
                                                _4__this,
                                                0.0,
                                                1.0,
                                                inDuration,
                                                inCurve,
                                                1,
                                                method_1);
  this->fields.__1__state = 1; /*0x1cf24bc*/
  return v12; /*0x1cf2508*/
}
