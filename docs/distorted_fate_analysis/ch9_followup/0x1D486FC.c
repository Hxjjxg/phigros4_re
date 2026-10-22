void Chapter9InputPasswordControl__Submit(Chapter9InputPasswordControl_o *this, const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 v9; // x1
  Il2CppObject *object; // x20
  UnityEngine_Behaviour_o *current; // x0
  const MethodInfo *method_1; // x1
  float v13; // s0
  float v14; // s1
  float v15; // s2
  float v16; // s0
  float v17; // s1
  float v18; // s2
  struct UnityEngine_UI_InputField_o *inputField; // x8
  System_String_o *m_Text; // x21
  float v21; // s0
  float v22; // s1
  float v23; // s2
  Chapter9InputPasswordControl___c_c *Chapter9InputPasswordControl.__c_TypeInfo; // x0
  System_Func_bool__o *_9__36_0; // x21
  Il2CppObject *_; // x22
  System_Action_o *todo; // x22

  if ( (byte_446E8EA & 1) == 0 ) /*0x1d48720*/
  {
    sub_1B622A0(&System_Action_TypeInfo, method); /*0x1d4872c*/
    sub_1B622A0(&UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo, v3); /*0x1d48738*/
    sub_1B622A0(&UnityEngine_EventSystems_EventSystem_TypeInfo, v4); /*0x1d48744*/
    sub_1B622A0(&System_Func_bool__TypeInfo, v5); /*0x1d48750*/
    sub_1B622A0(&Method_Chapter9InputPasswordControl___c__Submit_b__36_0__, v6); /*0x1d4875c*/
    sub_1B622A0(&Method_Chapter9InputPasswordControl___c__DisplayClass36_0__Submit_b__1__, v7); /*0x1d48768*/
    sub_1B622A0(&Chapter9InputPasswordControl___c__DisplayClass36_0_TypeInfo, v8); /*0x1d48774*/
    sub_1B622A0(&Chapter9InputPasswordControl___c_TypeInfo, v9); /*0x1d48780*/
    byte_446E8EA = 1; /*0x1d48788*/
  }
  object = (Il2CppObject *)sub_1B624E8(Chapter9InputPasswordControl___c__DisplayClass36_0_TypeInfo); /*0x1d48798*/
  System_Object___ctor(object, 0); /*0x1d4879c*/
  if ( !object ) /*0x1d487a0*/
    goto LABEL_20; /*0x1d487a0*/
  object[1].klass = (Il2CppClass *)this; /*0x1d487ac*/
  if ( !UnityEngine_EventSystems_EventSystem_TypeInfo->_2.cctor_finished ) /*0x1d487b4*/
    j_il2cpp_runtime_class_init_0(v13, v14, v15); /*0x1d487bc*/
  current = (UnityEngine_Behaviour_o *)UnityEngine_EventSystems_EventSystem__get_current(0); /*0x1d487c4*/
  object[1].monitor = current; /*0x1d487c8*/
  if ( !current /*0x1d487e8*/
    || (UnityEngine_Behaviour__set_enabled(current, 0, 0), (inputField = this->fields.inputField) == 0)
    || (m_Text = inputField->fields.m_Text) == 0 )
  {
LABEL_20:
    sub_1B624F8(current, method_1); /*0x1d488fc*/
  }
  if ( m_Text->fields._stringLength <= 0 ) /*0x1d487f4*/
  {
    Chapter9InputPasswordControl___c__DisplayClass36_0___Submit_g__OnWrongPassword_2( /*0x1d488f8*/
      (Chapter9InputPasswordControl___c__DisplayClass36_0_o *)object,
      method_1);
  }
  else
  {
    if ( !UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider_TypeInfo->_2.cctor_finished ) /*0x1d48808*/
      j_il2cpp_runtime_class_init_0(v16, v17, v18); /*0x1d48814*/
    UnityEngine_ResourceManagement_ResourceProviders_C9SecretAssetBundleProvider__SetPassword(m_Text, 0); /*0x1d48820*/
    Chapter9InputPasswordControl.__c_TypeInfo = Chapter9InputPasswordControl___c_TypeInfo; /*0x1d48824*/
    if ( !Chapter9InputPasswordControl___c_TypeInfo->_2.cctor_finished ) /*0x1d48828*/
    {
      j_il2cpp_runtime_class_init_0(v21, v22, v23); /*0x1d48830*/
      Chapter9InputPasswordControl.__c_TypeInfo = Chapter9InputPasswordControl___c_TypeInfo; /*0x1d48834*/
    }
    _9__36_0 = Chapter9InputPasswordControl.__c_TypeInfo->static_fields->__9__36_0; /*0x1d48844*/
    if ( !_9__36_0 ) /*0x1d48850*/
    {
      if ( !Chapter9InputPasswordControl.__c_TypeInfo->_2.cctor_finished ) /*0x1d48854*/
      {
        j_il2cpp_runtime_class_init_0(v21, v22, v23); /*0x1d4885c*/
        Chapter9InputPasswordControl.__c_TypeInfo = Chapter9InputPasswordControl___c_TypeInfo; /*0x1d48860*/
      }
      _ = (Il2CppObject *)Chapter9InputPasswordControl.__c_TypeInfo->static_fields->__9; /*0x1d48870*/
      _9__36_0 = (System_Func_bool__o *)sub_1B624E8(System_Func_bool__TypeInfo); /*0x1d4888c*/
      System_Func_bool____ctor(_9__36_0, _, Method_Chapter9InputPasswordControl___c__Submit_b__36_0__, 0); /*0x1d48894*/
      Chapter9InputPasswordControl___c_TypeInfo->static_fields->__9__36_0 = _9__36_0; /*0x1d488a0*/
    }
    todo = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo); /*0x1d488b8*/
    System_Action___ctor(todo, object, Method_Chapter9InputPasswordControl___c__DisplayClass36_0__Submit_b__1__, 0); /*0x1d488bc*/
    SimpleCoroutine__SimpleWaitUntil((UnityEngine_MonoBehaviour_o *)this, _9__36_0, todo, 0); /*0x1d488e0*/
  }
}
