void RetributionEffect__Start(RetributionEffect_o *this, const MethodInfo *method)
{
  RetributionEffect_o *this_1; // x19
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
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
  struct GameInformation_o *main; // x8
  struct LevelStartInfo_o *levelStartInfo; // x8
  System_String_o *songsLevel; // x20
  __int128 v21; // q0
  __int64 v22; // d1
  struct UnityEngine_GameObject_o *redFlash; // x22
  System_Delegate_o *klass; // x20
  System_Action_o *b; // x21
  System_Delegate_o *v26; // x0
  System_Action_c *System.Action_TypeInfo; // x1
  System_Delegate_o *monitor; // x20
  System_Action_o *b_1; // x21
  System_Action_c *System.Action_TypeInfo_1; // x1
  System_Delegate_o *m_CachedPtr; // x20
  System_Action_o *b_2; // x21
  System_Action_c *System.Action_TypeInfo_2; // x1
  System_Delegate_o *v34; // x20
  System_Action_o *b_3; // x21
  System_Action_c *System.Action_TypeInfo_3; // x1
  HPDisplayControl_c *HPDisplayControl_TypeInfo; // x8
  struct HPDisplayControl_StaticFields *static_fields; // x9
  struct HPDisplayControl_StaticFields *static_fields_1; // x9
  struct HPDisplayControl_StaticFields *static_fields_2; // x8
  RetributionEffect_o *this_2; // x21
  UnityEngine_GameObject_o *v42; // x20
  UnityEngine_Transform_o *transform; // x22
  struct LevelControl_o *levelControl; // x8
  struct LevelInformation_o *levelInformation; // x8
  RetributionEffect_o *v46; // x0
  const MethodInfo *method_1; // x1
  double judgeScale; // d0

  this_1 = this;
  if ( (byte_446E81C & 1) == 0 )
  {
    sub_1B622A0(&System_Action_TypeInfo, method);
    sub_1B622A0(&Method_UnityEngine_Component_GetComponent_LevelControl___, v3);
    sub_1B622A0(&Method_UnityEngine_GameObject_AddComponent_CanvasGroup___, v4);
    sub_1B622A0(&Method_UnityEngine_GameObject_AddComponent_Image___, v5);
    sub_1B622A0(&Method_UnityEngine_GameObject_GetComponent_Image___, v6);
    sub_1B622A0(&UnityEngine_GameObject_TypeInfo, v7);
    sub_1B622A0(&Method_RetributionEffect__Start_b__18_0__, v8);
    sub_1B622A0(&Method_RetributionEffect__Start_b__18_1__, v9);
    sub_1B622A0(&Method_RetributionEffect__Start_b__18_2__, v10);
    sub_1B622A0(&Method_RetributionEffect__Start_b__18_3__, v11);
    sub_1B622A0(&"HD", v12);
    sub_1B622A0(&"BG Mask", v13);
    sub_1B622A0(&"IN", v14);
    sub_1B622A0(&"EZ", v15);
    sub_1B622A0(&"Pause", v16);
    this = (RetributionEffect_o *)sub_1B622A0(&"UI", v17);
    byte_446E81C = 1;
  }
  if ( !byte_446E372 )
  {
    this = (RetributionEffect_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
    byte_446E372 = 1;
  }
  main = GameInformation_TypeInfo->static_fields->_main;
  if ( !main )
    goto LABEL_50;
  levelStartInfo = main->fields.levelStartInfo;
  if ( !levelStartInfo )
    goto LABEL_50;
  songsLevel = levelStartInfo->fields.songsLevel;
  if ( System_String__op_Equality(songsLevel, (System_String_o *)"EZ", 0) )
  {
    v21 = xmmword_C35020;
    v22 = 0x3F889374BC6A7EFALL;
LABEL_13:
    *(_OWORD *)&this_1->fields.beginHP = v21;
    *(_QWORD *)&this_1->fields.basicNoteAffect = v22;
    this_1->fields.noteAffect = 0.0259562842;
    goto LABEL_14;
  }
  if ( System_String__op_Equality(songsLevel, (System_String_o *)"HD", 0) )
  {
    v21 = xmmword_C34DD0;
    v22 = 0x3F8A9FBE76C8B439LL;
    goto LABEL_13;
  }
  if ( System_String__op_Equality(songsLevel, (System_String_o *)"IN", 0) )
  {
    v21 = xmmword_C35530;
    v22 = 0x3F8EB851EB851EB8LL;
    goto LABEL_13;
  }
LABEL_14:
  this = (RetributionEffect_o *)UnityEngine_Component__get_transform((UnityEngine_Component_o *)this_1, 0);
  if ( !this )
    goto LABEL_50;
  this = (RetributionEffect_o *)UnityEngine_Transform__get_parent((UnityEngine_Transform_o *)this, 0);
  if ( !this )
    goto LABEL_50;
  this = (RetributionEffect_o *)UnityEngine_Component__GetComponent_object_(
                                  (UnityEngine_Component_o *)this,
                                  (const MethodInfo_1F46C38 *)Method_UnityEngine_Component_GetComponent_LevelControl___);
  this_1->fields.levelControl = (struct LevelControl_o *)this;
  if ( !this )
    goto LABEL_50;
  redFlash = this->fields.redFlash;
  if ( !redFlash )
    goto LABEL_50;
  klass = (System_Delegate_o *)redFlash[8].klass;
  b = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
  System_Action___ctor(b, (Il2CppObject *)this_1, Method_RetributionEffect__Start_b__18_0__, 0);
  v26 = System_Delegate__Combine(klass, (System_Delegate_o *)b, 0);
  System.Action_TypeInfo = System_Action_TypeInfo;
  if ( !v26 || (System_Action_c *)v26->klass == System_Action_TypeInfo )
  {
    monitor = (System_Delegate_o *)redFlash[8].monitor;
    redFlash[8].klass = (UnityEngine_GameObject_c *)v26;
    b_1 = (System_Action_o *)sub_1B624E8(System.Action_TypeInfo);
    System_Action___ctor(b_1, (Il2CppObject *)this_1, Method_RetributionEffect__Start_b__18_1__, 0);
    v26 = System_Delegate__Combine(monitor, (System_Delegate_o *)b_1, 0);
    System.Action_TypeInfo_1 = System_Action_TypeInfo;
    if ( !v26 || (System_Action_c *)v26->klass == System_Action_TypeInfo )
    {
      m_CachedPtr = (System_Delegate_o *)redFlash[8].fields.m_CachedPtr;
      redFlash[8].monitor = v26;
      b_2 = (System_Action_o *)sub_1B624E8(System.Action_TypeInfo_1);
      System_Action___ctor(b_2, (Il2CppObject *)this_1, Method_RetributionEffect__Start_b__18_2__, 0);
      v26 = System_Delegate__Combine(m_CachedPtr, (System_Delegate_o *)b_2, 0);
      System.Action_TypeInfo_2 = System_Action_TypeInfo;
      if ( !v26 || (System_Action_c *)v26->klass == System_Action_TypeInfo )
      {
        v34 = (System_Delegate_o *)redFlash[9].klass;
        redFlash[8].fields.m_CachedPtr = (intptr_t)v26;
        b_3 = (System_Action_o *)sub_1B624E8(System.Action_TypeInfo_2);
        System_Action___ctor(b_3, (Il2CppObject *)this_1, Method_RetributionEffect__Start_b__18_3__, 0);
        v26 = System_Delegate__Combine(v34, (System_Delegate_o *)b_3, 0);
        if ( !v26
          || (System.Action_TypeInfo_3 = System_Action_TypeInfo, (System_Action_c *)v26->klass == System_Action_TypeInfo) )
        {
          redFlash[9].klass = (UnityEngine_GameObject_c *)v26;
          if ( !byte_446E86B )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            byte_446E86B = 1;
          }
          HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
          static_fields = HPDisplayControl_TypeInfo->static_fields;
          static_fields->_Opacity_k__BackingField = 0.0;
          if ( !byte_446E86C )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
            byte_446E86C = 1;
            static_fields = HPDisplayControl_TypeInfo->static_fields;
          }
          *(_QWORD *)&static_fields->_MainColor_k__BackingField.fields.r = 0;
          *(_QWORD *)&static_fields->_MainColor_k__BackingField.fields.b = 0;
          if ( !byte_446E86D )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
            byte_446E86D = 1;
          }
          static_fields_1 = HPDisplayControl_TypeInfo->static_fields;
          *(_QWORD *)&static_fields_1->_CoverColor_k__BackingField.fields.r = 0;
          *(_QWORD *)&static_fields_1->_CoverColor_k__BackingField.fields.b = 0;
          if ( !byte_446E86E )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
            byte_446E86E = 1;
          }
          static_fields_2 = HPDisplayControl_TypeInfo->static_fields;
          static_fields_2->_GlowOpacity_k__BackingField = 0.0;
          if ( !byte_446E86F )
          {
            sub_1B622A0(&HPDisplayControl_TypeInfo, System.Action_TypeInfo_3);
            byte_446E86F = 1;
            static_fields_2 = HPDisplayControl_TypeInfo->static_fields;
          }
          static_fields_2->_LockSlash_k__BackingField = 0;
          this = (RetributionEffect_o *)UnityEngine_GameObject__Find((System_String_o *)"UI", 0);
          if ( this )
          {
            this = (RetributionEffect_o *)UnityEngine_GameObject__get_transform((UnityEngine_GameObject_o *)this, 0);
            if ( this )
            {
              this_2 = this;
              this = (RetributionEffect_o *)UnityEngine_Transform__GetChild((UnityEngine_Transform_o *)this, 1, 0);
              if ( this )
              {
                this = (RetributionEffect_o *)UnityEngine_Component__get_gameObject((UnityEngine_Component_o *)this, 0);
                if ( this )
                {
                  this_1->fields.uiCanvasGroup = (struct UnityEngine_CanvasGroup_o *)UnityEngine_GameObject__AddComponent_object_(
                                                                                       (UnityEngine_GameObject_o *)this,
                                                                                       (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_CanvasGroup___);
                  v42 = (UnityEngine_GameObject_o *)sub_1B624E8(UnityEngine_GameObject_TypeInfo);
                  UnityEngine_GameObject___ctor(v42, (System_String_o *)"BG Mask", 0);
                  if ( v42 )
                  {
                    transform = UnityEngine_GameObject__get_transform(v42, 0);
                    this = (RetributionEffect_o *)UnityEngine_Transform__GetChild(
                                                    (UnityEngine_Transform_o *)this_2,
                                                    0,
                                                    0);
                    if ( this )
                    {
                      this = (RetributionEffect_o *)UnityEngine_Transform__GetChild(
                                                      (UnityEngine_Transform_o *)this,
                                                      0,
                                                      0);
                      if ( transform )
                      {
                        UnityEngine_Transform__set_parent(transform, (UnityEngine_Transform_o *)this, 0);
                        this = (RetributionEffect_o *)UnityEngine_GameObject__AddComponent_object_(
                                                        v42,
                                                        (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_Image___);
                        if ( this )
                        {
                          (*(void (__fastcall **)(RetributionEffect_o *, Il2CppMethodPointer, float, float, float, float))&this->klass[1]._2.naturalAligment)(
                            this,
                            this->klass[1].vtable._0_Equals.methodPtr,
                            0.0,
                            0.0,
                            0.0,
                            1.0);
                          this = (RetributionEffect_o *)UnityEngine_GameObject__AddComponent_object_(
                                                          v42,
                                                          (const MethodInfo_1F8E674 *)Method_UnityEngine_GameObject_AddComponent_CanvasGroup___);
                          this_1->fields.bgMask = (struct UnityEngine_CanvasGroup_o *)this;
                          if ( this )
                          {
                            UnityEngine_CanvasGroup__set_alpha((UnityEngine_CanvasGroup_o *)this, 0.0, 0);
                            this = (RetributionEffect_o *)UnityEngine_GameObject__Find(
                                                            (System_String_o *)"Pause",
                                                            0);
                            if ( this )
                            {
                              this = (RetributionEffect_o *)UnityEngine_GameObject__GetComponent_object_(
                                                              (UnityEngine_GameObject_o *)this,
                                                              (const MethodInfo_1F8E75C *)Method_UnityEngine_GameObject_GetComponent_Image___);
                              if ( this )
                              {
                                this = (RetributionEffect_o *)(*(__int64 (__fastcall **)(RetributionEffect_o *, Il2CppMethodPointer, float, float, float, float))&this->klass[1]._2.naturalAligment)(
                                                                this,
                                                                this->klass[1].vtable._0_Equals.methodPtr,
                                                                1.0,
                                                                1.0,
                                                                1.0,
                                                                0.2);
                                levelControl = this_1->fields.levelControl;
                                if ( levelControl )
                                {
                                  levelInformation = levelControl->fields.levelInformation;
                                  if ( levelInformation )
                                  {
                                    levelInformation->fields.canPause = 0;
                                    return;
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
LABEL_50:
          sub_1B624F8(this, method);
        }
      }
    }
  }
  v46 = (RetributionEffect_o *)sub_1B62898(v26);
  RetributionEffect__OnJudge(v46, judgeScale, method_1);
}
