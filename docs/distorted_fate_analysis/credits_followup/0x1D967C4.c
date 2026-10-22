void Setting__AboutUs(Setting_o *this, const MethodInfo *method)
{
  const MethodInfo *method_1; // x2
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  System_Collections_IEnumerator_o *routine; // x0
  float v8; // s0
  float v9; // s1
  float v10; // s2
  Il2CppObject *cutOut; // x19
  Il2CppObject *v12; // x0
  __int64 v13; // x1
  struct SEPlayer_play_o *Play; // x8

  if ( (byte_446EAD9 & 1) == 0 ) /*0x1d967ec*/
  {
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, method); /*0x1d967f8*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v4); /*0x1d96804*/
    sub_1B622A0(&SEPlayer_TypeInfo, v5); /*0x1d96810*/
    sub_1B622A0(&"AboutUs", v6); /*0x1d9681c*/
    byte_446EAD9 = 1; /*0x1d96824*/
  }
  routine = Setting__StartNextScene(this, (System_String_o *)"AboutUs", method_1); /*0x1d96840*/
  UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine, 0); /*0x1d96850*/
  cutOut = (Il2CppObject *)this->fields.cutOut; /*0x1d96858*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1d9685c*/
    j_il2cpp_runtime_class_init_0(v8, v9, v10); /*0x1d96864*/
  v12 = UnityEngine_Object__Instantiate_object__33364316( /*0x1d96870*/
          cutOut,
          (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
  Play = SEPlayer_TypeInfo->static_fields->Play; /*0x1d9687c*/
  if ( !Play ) /*0x1d96880*/
    sub_1B624F8(v12, v13); /*0x1d968a4*/
  ((void (__fastcall *)(intptr_t, __int64, intptr_t))Play->fields.invoke_impl)( /*0x1d968a0*/
    Play->fields.method_code,
    1,
    Play->fields.method);
}
