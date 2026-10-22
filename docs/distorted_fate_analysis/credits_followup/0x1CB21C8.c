void AboutUs__Back(AboutUs_o *this, const MethodInfo *method)
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
  __int64 v12; // x1
  __int64 v13; // x1
  __int64 v14; // x1
  __int64 v15; // x1
  __int64 v16; // x1
  __int64 v17; // x1
  __int64 v18; // x1
  __int64 v19; // x1
  __int64 v20; // x1
  __int64 v21; // x1
  __int64 v22; // x1
  __int64 v23; // x1
  __int64 v24; // x1
  struct Phigros2_VisionReplay_VisionReplay_StaticFields *static_fields; // x8
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo; // x0
  System_String_o *Chapter8Passed_k__BackingField; // x20
  __int64 v28; // x1
  float v29; // s0
  float v30; // s1
  float v31; // s2
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1; // x0
  System_String_o *_Chapter8Passed_k__BackingField; // x20
  __int64 v34; // x1
  float v35; // s0
  float v36; // s1
  float v37; // s2
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2; // x0
  float v39; // s0
  float v40; // s1
  float v41; // s2
  const MethodInfo *method_2; // x2
  float v43; // s0
  float v44; // s1
  float v45; // s2
  __int64 *p_StringLiteral_6161; // x8
  GetableItem_o *Component_object; // x0
  const MethodInfo *method_1; // x1
  float v49; // s0
  float v50; // s1
  float v51; // s2
  struct GameInformation_o *main; // x8
  struct GetCollectionControl_o *getCollectionControl; // x8
  System_Collections_Generic_IEnumerable_TSource__o *collectionItems; // x20
  AboutUs___c_c *AboutUs.__c_TypeInfo; // x0
  System_Func_object__bool__o *_9__17_0; // x21
  Il2CppObject *_; // x22
  System_Collections_Generic_IEnumerable_TSource__o *source; // x0
  __int64 v59; // x2
  int getPower; // w8
  GetableItem_o *Component_object_1; // x20
  unsigned int getPower_1; // w21
  System_Collections_IEnumerator_o *routine; // x0
  float v64; // s0
  float v65; // s1
  float v66; // s2
  Il2CppObject *cutOut; // x20

  if ( (byte_446E31A & 1) == 0 ) /*0x1cb21e4*/
  {
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_C8Unlock_TypeInfo, method); /*0x1cb21f0*/
    sub_1B622A0(&Method_System_Linq_Enumerable_ToArray_CollectionItemIndex___, v6); /*0x1cb21fc*/
    sub_1B622A0(&Method_System_Linq_Enumerable_Where_CollectionItemIndex___, v7); /*0x1cb2208*/
    sub_1B622A0(&System_Func_CollectionItemIndex__bool__TypeInfo, v8); /*0x1cb2214*/
    sub_1B622A0(&GameInformation_TypeInfo, v9); /*0x1cb2220*/
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Canvas___, v10); /*0x1cb222c*/
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v11); /*0x1cb2238*/
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v12); /*0x1cb2244*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v13); /*0x1cb2250*/
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Process_TypeInfo, v14); /*0x1cb225c*/
    sub_1B622A0(&SaveManagement_TypeInfo, v15); /*0x1cb2268*/
    sub_1B622A0(&Method_AboutUs___c__Back_b__17_0__, v16); /*0x1cb2274*/
    sub_1B622A0(&AboutUs___c_TypeInfo, v17); /*0x1cb2280*/
    sub_1B622A0(&"ChapterSelector", v18); /*0x1cb228c*/
    sub_1B622A0(&"chapter8Passed", v19); /*0x1cb2298*/
    sub_1B622A0(&"Top", v20); /*0x1cb22a4*/
    sub_1B622A0(&"DistortedFate.Sakuzyo.0", v21); /*0x1cb22b0*/
    sub_1B622A0(&"BootScene", v22); /*0x1cb22bc*/
    sub_1B622A0(&"Loading", v23); /*0x1cb22c8*/
    sub_1B622A0(&"C8DistortedFateUnlocked", v24); /*0x1cb22d4*/
    byte_446E31A = 1; /*0x1cb22dc*/
  }
  if ( !this->fields.onBack ) /*0x1cb22e0*/
  {
    this->fields.onBack = 1; /*0x1cb2300*/
    if ( !byte_446E376 ) /*0x1cb230c*/
    {
      sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method); /*0x1cb2318*/
      byte_446E376 = 1; /*0x1cb231c*/
    }
    static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1cb232c*/
    if ( static_fields->_IsOn_k__BackingField ) /*0x1cb2330*/
    {
      if ( !byte_446E377 ) /*0x1cb2340*/
      {
        sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method); /*0x1cb2348*/
        byte_446E377 = 1; /*0x1cb2354*/
        static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields; /*0x1cb2358*/
      }
      if ( static_fields->_IsSimulateMode_k__BackingField ) /*0x1cb235c*/
      {
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1cb2370*/
          j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cb2378*/
        if ( !byte_446E378 ) /*0x1cb2384*/
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, method); /*0x1cb2390*/
          byte_446E378 = 1; /*0x1cb2398*/
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1cb239c*/
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1cb23a0*/
        {
          j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cb23a8*/
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1cb23ac*/
        }
        Chapter8Passed_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo->static_fields->_Chapter8Passed_k__BackingField; /*0x1cb23c0*/
        if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cb23c4*/
          j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cb23d0*/
        SaveManagement__SaveBool(Chapter8Passed_k__BackingField, 1, 0); /*0x1cb23e4*/
        if ( !byte_446E378 ) /*0x1cb23ec*/
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v28); /*0x1cb23f8*/
          byte_446E378 = 1; /*0x1cb23fc*/
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1cb2400*/
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1cb2404*/
        {
          j_il2cpp_runtime_class_init_0(v29, v30, v31); /*0x1cb240c*/
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1cb2410*/
        }
        _Chapter8Passed_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1->static_fields->_Chapter8Passed_k__BackingField; /*0x1cb2424*/
        if ( !Phigros2_VisionReplay_VisionReplay_Process_TypeInfo->_2.cctor_finished ) /*0x1cb2428*/
          j_il2cpp_runtime_class_init_0(v29, v30, v31); /*0x1cb2434*/
        Phigros2_VisionReplay_VisionReplay_Process__SetValue(_Chapter8Passed_k__BackingField, 1, 0); /*0x1cb2448*/
        if ( !byte_446E379 ) /*0x1cb2454*/
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v34); /*0x1cb2460*/
          byte_446E379 = 1; /*0x1cb2464*/
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1cb2468*/
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished ) /*0x1cb246c*/
        {
          j_il2cpp_runtime_class_init_0(v35, v36, v37); /*0x1cb2474*/
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo; /*0x1cb2478*/
        }
        Phigros2_VisionReplay_VisionReplay_Process__SetValue( /*0x1cb248c*/
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2->static_fields->_C8DistortedFateUnlocked_k__BackingField,
          1,
          0);
        if ( !Phigros2_VisionReplay_VisionReplay_C8Unlock_TypeInfo->_2.cctor_finished ) /*0x1cb249c*/
          j_il2cpp_runtime_class_init_0(v39, v40, v41); /*0x1cb24a4*/
        Phigros2_VisionReplay_VisionReplay_C8Unlock__SetValue((System_String_o *)"DistortedFate.Sakuzyo.0", 1, 0); /*0x1cb24bc*/
      }
    }
    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cb24cc*/
      j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1cb24d4*/
    if ( SaveManagement__LoadBool((System_String_o *)"chapter8Passed", 0, 0) ) /*0x1cb24ec*/
    {
      GameInformation_TypeInfo->static_fields->nextScene = (struct System_String_o *)"ChapterSelector"; /*0x1cb2510*/
      p_StringLiteral_6161 = &"Loading"; /*0x1cb2518*/
    }
    else
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cb2524*/
        j_il2cpp_runtime_class_init_0(v43, v44, v45); /*0x1cb252c*/
      SaveManagement__SaveBool((System_String_o *)"chapter8Passed", 1, 0); /*0x1cb2540*/
      SaveManagement__SaveBool((System_String_o *)"C8DistortedFateUnlocked", 1, 0); /*0x1cb2558*/
      if ( !byte_446E372 ) /*0x1cb2564*/
      {
        Component_object = (GetableItem_o *)sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cb2570*/
        byte_446E372 = 1; /*0x1cb2574*/
      }
      main = GameInformation_TypeInfo->static_fields->_main; /*0x1cb2588*/
      if ( !main ) /*0x1cb258c*/
        goto LABEL_61; /*0x1cb258c*/
      getCollectionControl = main->fields.getCollectionControl; /*0x1cb2590*/
      if ( !getCollectionControl ) /*0x1cb2594*/
        goto LABEL_61; /*0x1cb2594*/
      collectionItems = (System_Collections_Generic_IEnumerable_TSource__o *)getCollectionControl->fields.collectionItems; /*0x1cb25a0*/
      AboutUs.__c_TypeInfo = AboutUs___c_TypeInfo; /*0x1cb25a4*/
      if ( !AboutUs___c_TypeInfo->_2.cctor_finished ) /*0x1cb25a8*/
      {
        j_il2cpp_runtime_class_init_0(v49, v50, v51); /*0x1cb25b0*/
        AboutUs.__c_TypeInfo = AboutUs___c_TypeInfo; /*0x1cb25b4*/
      }
      _9__17_0 = (System_Func_object__bool__o *)AboutUs.__c_TypeInfo->static_fields->__9__17_0; /*0x1cb25bc*/
      if ( !_9__17_0 ) /*0x1cb25c0*/
      {
        if ( !AboutUs.__c_TypeInfo->_2.cctor_finished ) /*0x1cb25c4*/
        {
          j_il2cpp_runtime_class_init_0(v49, v50, v51); /*0x1cb25cc*/
          AboutUs.__c_TypeInfo = AboutUs___c_TypeInfo; /*0x1cb25d0*/
        }
        _ = (Il2CppObject *)AboutUs.__c_TypeInfo->static_fields->__9; /*0x1cb25e0*/
        _9__17_0 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_CollectionItemIndex__bool__TypeInfo); /*0x1cb25fc*/
        System_Func_object__bool____ctor(_9__17_0, _, Method_AboutUs___c__Back_b__17_0__, 0); /*0x1cb2604*/
        AboutUs___c_TypeInfo->static_fields->__9__17_0 = (struct System_Func_CollectionItemIndex__bool__o *)_9__17_0; /*0x1cb2610*/
      }
      source = System_Linq_Enumerable__Where_object_( /*0x1cb2628*/
                 collectionItems,
                 (System_Func_TSource__bool__o *)_9__17_0,
                 (const MethodInfo_1F7E054 *)Method_System_Linq_Enumerable_Where_CollectionItemIndex___);
      Component_object = (GetableItem_o *)System_Linq_Enumerable__ToArray_object_( /*0x1cb2638*/
                                            source,
                                            (const MethodInfo_1F7ABAC *)Method_System_Linq_Enumerable_ToArray_CollectionItemIndex___);
      if ( !Component_object ) /*0x1cb263c*/
        goto LABEL_61; /*0x1cb263c*/
      getPower = Component_object->fields.getPower; /*0x1cb2640*/
      Component_object_1 = Component_object; /*0x1cb2644*/
      if ( getPower >= 1 ) /*0x1cb264c*/
      {
        getPower_1 = 0; /*0x1cb2650*/
        do /*0x1cb2654*/
        {
          if ( getPower_1 >= getPower ) /*0x1cb2658*/
            sub_1B62500(Component_object, method_1, v59); /*0x1cb2758*/
          Component_object = (GetableItem_o *)*((_QWORD *)&Component_object_1->fields.getInfo1 + (int)getPower_1); /*0x1cb2660*/
          if ( !Component_object ) /*0x1cb2664*/
            goto LABEL_61; /*0x1cb2664*/
          GetableItem__Get(Component_object, method_1); /*0x1cb2668*/
          getPower = Component_object_1->fields.getPower; /*0x1cb266c*/
        }
        while ( (int)++getPower_1 < getPower ); /*0x1cb2654*/
      }
      UnityEngine_PlayerPrefs__Save(0); /*0x1cb267c*/
      GameInformation_TypeInfo->static_fields->nextScene = (struct System_String_o *)"ChapterSelector"; /*0x1cb2698*/
      p_StringLiteral_6161 = &"BootScene"; /*0x1cb26a0*/
    }
    routine = AboutUs__StartNextScene(this, (System_String_o *)*p_StringLiteral_6161, method_2); /*0x1cb26ac*/
    UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine, 0); /*0x1cb26bc*/
    cutOut = (Il2CppObject *)this->fields.cutOut; /*0x1cb26c8*/
    if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1cb26d0*/
      j_il2cpp_runtime_class_init_0(v64, v65, v66); /*0x1cb26d8*/
    Component_object = (GetableItem_o *)UnityEngine_Object__Instantiate_object__33364316( /*0x1cb26ec*/
                                          cutOut,
                                          (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
    if ( Component_object ) /*0x1cb26f0*/
    {
      Component_object = (GetableItem_o *)UnityEngine_GameObject__GetComponent_object_( /*0x1cb2700*/
                                            (UnityEngine_GameObject_o *)Component_object,
                                            (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
      if ( Component_object ) /*0x1cb2704*/
      {
        UnityEngine_Canvas__set_sortingLayerName( /*0x1cb2718*/
          (UnityEngine_Canvas_o *)Component_object,
          (System_String_o *)"Top",
          0);
        Component_object = (GetableItem_o *)UnityEngine_Object__Instantiate_object__33364316( /*0x1cb2724*/
                                              (Il2CppObject *)this->fields.cutOut,
                                              (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
        if ( Component_object ) /*0x1cb2728*/
        {
          Component_object = (GetableItem_o *)UnityEngine_GameObject__GetComponent_object_( /*0x1cb2730*/
                                                (UnityEngine_GameObject_o *)Component_object,
                                                (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
          if ( Component_object ) /*0x1cb2734*/
          {
            UnityEngine_Canvas__set_sortingOrder((UnityEngine_Canvas_o *)Component_object, 10, 0); /*0x1cb2750*/
            return; /*0x1cb2750*/
          }
        }
      }
    }
LABEL_61:
    sub_1B624F8(Component_object, method_1); /*0x1cb2754*/
  }
}
