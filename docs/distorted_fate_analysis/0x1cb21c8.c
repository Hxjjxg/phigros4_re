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
  __int64 v49; // x2
  float v50; // s0
  float v51; // s1
  float v52; // s2
  struct GameInformation_o *main; // x8
  struct GetCollectionControl_o *getCollectionControl; // x8
  System_Collections_Generic_IEnumerable_TSource__o *collectionItems; // x20
  AboutUs___c_c *AboutUs.__c_TypeInfo; // x0
  System_Func_object__bool__o *_9__17_0; // x21
  Il2CppObject *_; // x22
  System_Collections_Generic_IEnumerable_TSource__o *source; // x0
  int getPower; // w8
  GetableItem_o *Component_object_1; // x20
  unsigned int getPower_1; // w21
  System_Collections_IEnumerator_o *routine; // x0
  float v64; // s0
  float v65; // s1
  float v66; // s2
  Il2CppObject *cutOut; // x20

  if ( (byte_446E31A & 1) == 0 )
  {
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_C8Unlock_TypeInfo, method);
    sub_1B622A0(&Method_System_Linq_Enumerable_ToArray_CollectionItemIndex___, v6);
    sub_1B622A0(&Method_System_Linq_Enumerable_Where_CollectionItemIndex___, v7);
    sub_1B622A0(&System_Func_CollectionItemIndex__bool__TypeInfo, v8);
    sub_1B622A0(&GameInformation_TypeInfo, v9);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Canvas___, v10);
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v11);
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v12);
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v13);
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Process_TypeInfo, v14);
    sub_1B622A0(&SaveManagement_TypeInfo, v15);
    sub_1B622A0(&Method_AboutUs___c__Back_b__17_0__, v16);
    sub_1B622A0(&AboutUs___c_TypeInfo, v17);
    sub_1B622A0(&"ChapterSelector", v18);
    sub_1B622A0(&"chapter8Passed", v19);
    sub_1B622A0(&"Top", v20);
    sub_1B622A0(&"DistortedFate.Sakuzyo.0", v21);
    sub_1B622A0(&"BootScene", v22);
    sub_1B622A0(&"Loading", v23);
    sub_1B622A0(&"C8DistortedFateUnlocked", v24);
    byte_446E31A = 1;
  }
  if ( !this->fields.onBack )
  {
    this->fields.onBack = 1;
    if ( !byte_446E376 )
    {
      sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method);
      byte_446E376 = 1;
    }
    static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields;
    if ( static_fields->_IsOn_k__BackingField )
    {
      if ( !byte_446E377 )
      {
        sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_TypeInfo, method);
        byte_446E377 = 1;
        static_fields = Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields;
      }
      if ( static_fields->_IsSimulateMode_k__BackingField )
      {
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v2, v3, v4);
        if ( !byte_446E378 )
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, method);
          byte_446E378 = 1;
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v2, v3, v4);
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        }
        Chapter8Passed_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo->static_fields->_Chapter8Passed_k__BackingField;
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v2, v3, v4);
        SaveManagement__SaveBool(Chapter8Passed_k__BackingField, 1, 0);
        if ( !byte_446E378 )
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v28);
          byte_446E378 = 1;
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v29, v30, v31);
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        }
        _Chapter8Passed_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1->static_fields->_Chapter8Passed_k__BackingField;
        if ( !Phigros2_VisionReplay_VisionReplay_Process_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v29, v30, v31);
        Phigros2_VisionReplay_VisionReplay_Process__SetValue(_Chapter8Passed_k__BackingField, 1, 0);
        if ( !byte_446E379 )
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v34);
          byte_446E379 = 1;
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v35, v36, v37);
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        }
        Phigros2_VisionReplay_VisionReplay_Process__SetValue(
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_2->static_fields->_C8DistortedFateUnlocked_k__BackingField,
          1,
          0);
        if ( !Phigros2_VisionReplay_VisionReplay_C8Unlock_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v39, v40, v41);
        Phigros2_VisionReplay_VisionReplay_C8Unlock__SetValue((System_String_o *)"DistortedFate.Sakuzyo.0", 1, 0);
      }
    }
    if ( !SaveManagement_TypeInfo->_2.cctor_finished )
      j_il2cpp_runtime_class_init_0(v2, v3, v4);
    if ( SaveManagement__LoadBool((System_String_o *)"chapter8Passed", 0, 0) )
    {
      GameInformation_TypeInfo->static_fields->nextScene = (struct System_String_o *)"ChapterSelector";
      p_StringLiteral_6161 = &"Loading";
    }
    else
    {
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v43, v44, v45);
      SaveManagement__SaveBool((System_String_o *)"chapter8Passed", 1, 0);
      SaveManagement__SaveBool((System_String_o *)"C8DistortedFateUnlocked", 1, 0);
      if ( !byte_446E372 )
      {
        Component_object = (GetableItem_o *)sub_1B622A0(&GameInformation_TypeInfo, method_1);
        byte_446E372 = 1;
      }
      main = GameInformation_TypeInfo->static_fields->_main;
      if ( !main )
        goto LABEL_61;
      getCollectionControl = main->fields.getCollectionControl;
      if ( !getCollectionControl )
        goto LABEL_61;
      collectionItems = (System_Collections_Generic_IEnumerable_TSource__o *)getCollectionControl->fields.collectionItems;
      AboutUs.__c_TypeInfo = AboutUs___c_TypeInfo;
      if ( !AboutUs___c_TypeInfo->_2.cctor_finished )
      {
        j_il2cpp_runtime_class_init_0(v50, v51, v52);
        AboutUs.__c_TypeInfo = AboutUs___c_TypeInfo;
      }
      _9__17_0 = (System_Func_object__bool__o *)AboutUs.__c_TypeInfo->static_fields->__9__17_0;
      if ( !_9__17_0 )
      {
        if ( !AboutUs.__c_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v50, v51, v52);
          AboutUs.__c_TypeInfo = AboutUs___c_TypeInfo;
        }
        _ = (Il2CppObject *)AboutUs.__c_TypeInfo->static_fields->__9;
        _9__17_0 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_CollectionItemIndex__bool__TypeInfo);
        System_Func_object__bool____ctor(_9__17_0, _, Method_AboutUs___c__Back_b__17_0__, 0);
        AboutUs___c_TypeInfo->static_fields->__9__17_0 = (struct System_Func_CollectionItemIndex__bool__o *)_9__17_0;
      }
      source = System_Linq_Enumerable__Where_object_(
                 collectionItems,
                 (System_Func_TSource__bool__o *)_9__17_0,
                 (const MethodInfo_1F7E054 *)Method_System_Linq_Enumerable_Where_CollectionItemIndex___);
      Component_object = (GetableItem_o *)System_Linq_Enumerable__ToArray_object_(
                                            source,
                                            (const MethodInfo_1F7ABAC *)Method_System_Linq_Enumerable_ToArray_CollectionItemIndex___);
      if ( !Component_object )
        goto LABEL_61;
      getPower = Component_object->fields.getPower;
      Component_object_1 = Component_object;
      if ( getPower >= 1 )
      {
        getPower_1 = 0;
        do
        {
          if ( getPower_1 >= getPower )
            sub_1B62500(Component_object, method_1, v49);
          Component_object = (GetableItem_o *)*((_QWORD *)&Component_object_1->fields.getInfo1 + (int)getPower_1);
          if ( !Component_object )
            goto LABEL_61;
          GetableItem__Get(Component_object, method_1);
          getPower = Component_object_1->fields.getPower;
        }
        while ( (int)++getPower_1 < getPower );
      }
      UnityEngine_PlayerPrefs__Save(0);
      GameInformation_TypeInfo->static_fields->nextScene = (struct System_String_o *)"ChapterSelector";
      p_StringLiteral_6161 = &"BootScene";
    }
    routine = AboutUs__StartNextScene(this, (System_String_o *)*p_StringLiteral_6161, method_2);
    UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine, 0);
    cutOut = (Il2CppObject *)this->fields.cutOut;
    if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
      j_il2cpp_runtime_class_init_0(v64, v65, v66);
    Component_object = (GetableItem_o *)UnityEngine_Object__Instantiate_object__33364316(
                                          cutOut,
                                          (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
    if ( Component_object )
    {
      Component_object = (GetableItem_o *)UnityEngine_GameObject__GetComponent_object_(
                                            (UnityEngine_GameObject_o *)Component_object,
                                            (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
      if ( Component_object )
      {
        UnityEngine_Canvas__set_sortingLayerName(
          (UnityEngine_Canvas_o *)Component_object,
          (System_String_o *)"Top",
          0);
        Component_object = (GetableItem_o *)UnityEngine_Object__Instantiate_object__33364316(
                                              (Il2CppObject *)this->fields.cutOut,
                                              (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
        if ( Component_object )
        {
          Component_object = (GetableItem_o *)UnityEngine_GameObject__GetComponent_object_(
                                                (UnityEngine_GameObject_o *)Component_object,
                                                (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Canvas___);
          if ( Component_object )
          {
            UnityEngine_Canvas__set_sortingOrder((UnityEngine_Canvas_o *)Component_object, 10, 0);
            return;
          }
        }
      }
    }
LABEL_61:
    sub_1B624F8(Component_object, method_1, v49);
  }
}
