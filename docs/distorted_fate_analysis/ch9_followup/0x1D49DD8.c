void Chapter9InputPasswordControl___c__DisplayClass36_0___Submit_b__3(
        Chapter9InputPasswordControl___c__DisplayClass36_0_o *this,
        const MethodInfo *method)
{
  struct Chapter9InputPasswordControl_o *_4__this; // x8
  Chapter9InputPasswordControl___c__DisplayClass36_0_o *this_1; // x19
  UnityEngine_MonoBehaviour_o *__4__this; // x19
  System_Collections_IEnumerator_o *routine; // x1

  _4__this = this->fields.__4__this; /*0x1d49ddc*/
  if ( !_4__this /*0x1d49dfc*/
    || (this_1 = this,
        (this = (Chapter9InputPasswordControl___c__DisplayClass36_0_o *)_4__this->fields.phase1Control) == 0)
    || (Phigros2_Chapter9_Chapter9Phase1Control__FadeOutBgmOnLeave((Phigros2_Chapter9_Chapter9Phase1Control_o *)this, 0),
        (__4__this = (UnityEngine_MonoBehaviour_o *)this_1->fields.__4__this) == 0) )
  {
    sub_1B624F8(this, method); /*0x1d49e18*/
  }
  routine = Chapter9InputPasswordControl__FadeToBlackThenStartC9S6((Chapter9InputPasswordControl_o *)this, method); /*0x1d49e04*/
  UnityEngine_MonoBehaviour__StartCoroutine_63287500(__4__this, routine, 0); /*0x1d49e14*/
}
