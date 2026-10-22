void Chapter9InputPasswordControl___c__DisplayClass36_0___Submit_b__1(
        Chapter9InputPasswordControl___c__DisplayClass36_0_o *this,
        const MethodInfo *method)
{
  float v2; // s0
  float v3; // s1
  float v4; // s2
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 v9; // x1
  __int64 v10; // x1
  __int64 v11; // x1
  Chapter9InputPasswordControl_o *UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB; // x0
  struct Chapter9InputPasswordControl_o *_4__this; // x8
  struct UnityEngine_UI_InputField_o *inputField; // x8
  System_String_o *m_Text; // x20
  const MethodInfo *method_1; // x3
  System_Action_o *_9__3; // x21
  UnityEngine_MonoBehaviour_o *__4__this; // x20

  if ( (byte_446E8F8 & 1) == 0 ) /*0x1d49c54*/
  {
    sub_1B622A0(&System_Action_TypeInfo, method); /*0x1d49c60*/
    sub_1B622A0(&UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo, v6); /*0x1d49c6c*/
    sub_1B622A0(&Method_System_Nullable_bool__GetValueOrDefault__, v7); /*0x1d49c78*/
    sub_1B622A0(&Method_System_Nullable_bool__get_HasValue__, v8); /*0x1d49c84*/
    sub_1B622A0(&SaveManagement_TypeInfo, v9); /*0x1d49c90*/
    sub_1B622A0(&Method_Chapter9InputPasswordControl___c__DisplayClass36_0__Submit_b__3__, v10); /*0x1d49c9c*/
    sub_1B622A0(&StringLiteral_3172, v11); /*0x1d49ca8*/
    byte_446E8F8 = 1; /*0x1d49cb0*/
  }
  if ( !UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo->_2.cctor_finished ) /*0x1d49cb8*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d49cc0*/
  if ( !byte_446E6A8 ) /*0x1d49ccc*/
  {
    sub_1B622A0(&UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo, method); /*0x1d49cd8*/
    byte_446E6A8 = 1; /*0x1d49ce0*/
  }
  UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB = (Chapter9InputPasswordControl_o *)UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo; /*0x1d49ce4*/
  if ( !UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo->_2.cctor_finished ) /*0x1d49ce8*/
  {
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d49cf0*/
    UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB = (Chapter9InputPasswordControl_o *)UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo; /*0x1d49cf4*/
  }
  if ( LOWORD(UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB->fields.changeColorRoutine->monitor) >= 0x100u /*0x1d49d08*/
    && (unsigned __int8)LOWORD(UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB->fields.changeColorRoutine->monitor) )
  {
    _4__this = this->fields.__4__this; /*0x1d49d10*/
    if ( !_4__this ) /*0x1d49d14*/
      goto LABEL_20; /*0x1d49d14*/
    inputField = _4__this->fields.inputField; /*0x1d49d18*/
    if ( !inputField ) /*0x1d49d1c*/
      goto LABEL_20; /*0x1d49d1c*/
    m_Text = inputField->fields.m_Text; /*0x1d49d28*/
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d49d30*/
      j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d49d38*/
    SaveManagement__SaveString((System_String_o *)StringLiteral_3172, m_Text, 0); /*0x1d49d50*/
    UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB = this->fields.__4__this; /*0x1d49d54*/
    if ( !UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB ) /*0x1d49d58*/
LABEL_20:
      sub_1B624F8(UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB, method); /*0x1d49dd4*/
    Chapter9InputPasswordControl__PlayPasswordSe( /*0x1d49d64*/
      UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB,
      UnityEngine.ResourceManagement.ResourceProviders.C9SecretAssetB->fields.correctPasswordClip,
      1,
      method_1);
    _9__3 = this->fields.__9__3; /*0x1d49d68*/
    __4__this = (UnityEngine_MonoBehaviour_o *)this->fields.__4__this; /*0x1d49d6c*/
    if ( !_9__3 ) /*0x1d49d70*/
    {
      _9__3 = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo); /*0x1d49d94*/
      System_Action___ctor( /*0x1d49d9c*/
        _9__3,
        (Il2CppObject *)this,
        Method_Chapter9InputPasswordControl___c__DisplayClass36_0__Submit_b__3__,
        0);
      this->fields.__9__3 = _9__3; /*0x1d49da0*/
    }
    SimpleCoroutine__SimpleDelay(__4__this, 0.3, _9__3, 0); /*0x1d49dc0*/
  }
  else
  {
    Chapter9InputPasswordControl___c__DisplayClass36_0___Submit_g__OnWrongPassword_2(this, method); /*0x1d49dd0*/
  }
}
