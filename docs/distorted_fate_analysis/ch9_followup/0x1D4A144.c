bool Chapter9InputPasswordControl__FadeToBlackThenStartC9S6_d__39__MoveNext(
        Chapter9InputPasswordControl__FadeToBlackThenStartC9S6_d__39_o *this,
        const MethodInfo *method)
{
  int32_t _1__state; // w8

  _1__state = this->fields.__1__state; /*0x1d4a148*/
  if ( _1__state == 1 ) /*0x1d4a154*/
  {
    this->fields.__1__state = -1; /*0x1d4a18c*/
    Phigros2_Chapter9_C9S6Unlock__StartC9S6(0); /*0x1d4a190*/
  }
  else if ( !_1__state ) /*0x1d4a158*/
  {
    this->fields.__1__state = -1; /*0x1d4a16c*/
    this->fields.__2__current = (Il2CppObject *)Phigros2_Chapter9_C9S6Unlock__FadeSceneMask(0.0, 1.0, 0); /*0x1d4a174*/
    this->fields.__1__state = 1; /*0x1d4a17c*/
    return 1; /*0x1d4a180*/
  }
  return 0; /*0x1d4a19c*/
}
