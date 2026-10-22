void C9AfterDeductionControl__Back(C9AfterDeductionControl_o *this, const MethodInfo *method)
{
  const MethodInfo *method_1; // x2
  C9AfterDeductionControl_o *this_1; // x19
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  System_Collections_IEnumerator_o *routine; // x0
  float v9; // s0
  float v10; // s1
  float v11; // s2
  Il2CppObject *cutOut; // x19
  Il2CppObject *v13; // x0
  __int64 v14; // x1

  this_1 = this; /*0x1d3fc10*/
  if ( (byte_446E89A & 1) == 0 ) /*0x1d3fc14*/
  {
    sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d3fc20*/
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v4); /*0x1d3fc2c*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v5); /*0x1d3fc38*/
    sub_1B622A0(&"ChapterSelector", v6); /*0x1d3fc44*/
    this = (C9AfterDeductionControl_o *)sub_1B622A0(&"BootScene", v7); /*0x1d3fc50*/
    byte_446E89A = 1; /*0x1d3fc58*/
  }
  if ( !this_1->fields.onBack ) /*0x1d3fc5c*/
  {
    this_1->fields.onBack = 1; /*0x1d3fc7c*/
    GameInformation_TypeInfo->static_fields->nextScene = (struct System_String_o *)"ChapterSelector"; /*0x1d3fc94*/
    routine = C9AfterDeductionControl__StartNextScene(this, (System_String_o *)"BootScene", method_1); /*0x1d3fca4*/
    UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this_1, routine, 0); /*0x1d3fcb4*/
    cutOut = (Il2CppObject *)this_1->fields.cutOut; /*0x1d3fcc0*/
    if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1d3fcc8*/
      j_il2cpp_runtime_class_init_0(v9, v10, v11); /*0x1d3fcd0*/
    v13 = UnityEngine_Object__Instantiate_object__33364316( /*0x1d3fce4*/
            cutOut,
            (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
    if ( !v13 ) /*0x1d3fce8*/
      sub_1B624F8(0, v14); /*0x1d3fd00*/
    UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)v13, 1, 0); /*0x1d3fcfc*/
  }
}
