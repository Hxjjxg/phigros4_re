void RetributionEffectSecondPhase__Update(RetributionEffectSecondPhase_o *this, const MethodInfo *method)
{
  const MethodInfo *method_1; // x2
  RetributionEffectSecondPhase_o *this_1; // x19
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  struct LevelControl_o *levelControl; // x8
  struct ProgressControl_o *progressControl; // x9
  float b; // s8
  float outro; // s8
  float deltaTime; // s0
  float outro_1; // s0
  struct LevelControl_o *levelControl_1; // x8
  struct ProgressControl_o *progressControl_1; // x8
  struct AddressableAudioSource_o *audioSource; // x8
  struct UnityEngine_GameObject_o *redFlash; // x22
  struct LevelControl_o *levelControl_2; // x8
  LevelRecord_o *this_2; // x19
  float v21; // s0
  float v22; // s1
  float v23; // s2
  struct GameInformation_o *main; // x8
  UnityEngine_Object_o *background; // x19
  float v26; // s0
  float v27; // s1
  float v28; // s2
  float nowTime; // s0
  float v30; // s9
  float v31; // s0
  RetributionEffectSecondPhase_o *bgMask; // x19
  float v33; // s0
  float value; // s0
  HPDisplayControl_c *HPDisplayControl_TypeInfo_1; // x8
  struct HPDisplayControl_StaticFields *static_fields_2; // x8
  System_Collections_IEnumerator_o *routine_1; // x0
  double timeAffect; // d1
  double v39; // d0
  double v40; // d8
  struct JudgeControl_o *judgeControl; // x10
  struct LevelInformation_o *levelInformation; // x8
  Il2CppObject *redFlash_1; // x20
  System_Action_o *todo; // x20
  struct LevelControl_o *levelControl_3; // x8
  struct ProgressControl_o *progressControl_2; // x8
  System_Collections_IEnumerator_o *routine; // x0
  double v48; // d10
  float displayHP; // s8
  float v50; // s0
  float v51; // s10
  bool v52; // nf
  float v53; // s0
  float v54; // s0
  float displayHP_2; // s1
  float displayHP_1; // s0
  float v57; // s8
  HPDisplayControl_c *HPDisplayControl_TypeInfo; // x8
  float v59; // s1
  struct HPDisplayControl_StaticFields *static_fields; // x9
  float v61; // s0
  float32x4_t _MainColor_k__BackingField; // q0
  float v63; // s0
  float v64; // s1
  float a; // s9
  struct HPDisplayControl_StaticFields *static_fields_1; // x9
  float v67; // s0
  float _GlowOpacity_k__BackingField; // s8
  float v69; // s0
  float value_2; // s1
  float value_1; // s0
  float32x4_t _MainColor_k__BackingField_1; // [xsp+0h] [xbp-70h]

  this_1 = this;
  if ( (byte_446E822 & 1) == 0 )
  {
    sub_1B622A0(&System_Action_TypeInfo, method);
    sub_1B622A0(&Method_UnityEngine_Object_Instantiate_GameObject___, v4);
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v5);
    sub_1B622A0(&Method_RetributionEffectSecondPhase__Update_b__26_0__, v6);
    sub_1B622A0(&UnityEngine_SceneManagement_SceneManager_TypeInfo, v7);
    this = (RetributionEffectSecondPhase_o *)sub_1B622A0(&"DFUnlock", v8);
    byte_446E822 = 1;
  }
  levelControl = this_1->fields.levelControl;
  if ( !levelControl )
    goto LABEL_91;
  progressControl = levelControl->fields.progressControl;
  if ( !progressControl )
    goto LABEL_91;
  if ( this_1->fields.locked )
  {
    this = (RetributionEffectSecondPhase_o *)progressControl->fields.audioSource;
    if ( !this )
      goto LABEL_91;
    b = this->fields.startColor.fields.b;
    if ( b > (float)(AddressableAudioSource__get_ClipLength((AddressableAudioSource_o *)this, 0) + -6.5) )
    {
      outro = this_1->fields.outro;
      deltaTime = UnityEngine_Time__get_deltaTime(0);
      this = (RetributionEffectSecondPhase_o *)this_1->fields.lockAnimImage;
      outro_1 = fmaxf(outro - deltaTime, 0.0);
      this_1->fields.outro = outro_1;
      if ( !this )
        goto LABEL_91;
      this = (RetributionEffectSecondPhase_o *)(*(__int64 (__fastcall **)(RetributionEffectSecondPhase_o *, Il2CppMethodPointer, float, float, float, float))&this->klass[1]._2.naturalAligment)(
                                                 this,
                                                 this->klass[1].vtable._0_Equals.methodPtr,
                                                 0.0,
                                                 0.0,
                                                 0.0,
                                                 (float)(outro_1 / -1.8) + 1.0);
    }
    if ( this_1->fields.outro != 0.0 )
      return;
    levelControl_1 = this_1->fields.levelControl;
    if ( levelControl_1 )
    {
      progressControl_1 = levelControl_1->fields.progressControl;
      if ( progressControl_1 )
      {
        audioSource = progressControl_1->fields.audioSource;
        if ( audioSource )
        {
          this = (RetributionEffectSecondPhase_o *)audioSource->fields.audioSource;
          if ( this )
          {
            if ( UnityEngine_AudioSource__get_isPlaying((UnityEngine_AudioSource_o *)this, 0) || this_1->fields.isOver )
              return;
            this_1->fields.isOver = 1;
            if ( !byte_446E372 )
            {
              sub_1B622A0(&GameInformation_TypeInfo, method);
              byte_446E372 = 1;
            }
            this = (RetributionEffectSecondPhase_o *)GameInformation_TypeInfo->static_fields->_main;
            if ( this )
            {
              redFlash = this->fields.redFlash;
              if ( redFlash )
              {
                this = (RetributionEffectSecondPhase_o *)GameInformation__GetSongRecord(
                                                           (GameInformation_o *)this,
                                                           (System_String_o *)redFlash->fields.m_CachedPtr,
                                                           (System_String_o *)redFlash[2].monitor,
                                                           0);
                levelControl_2 = this_1->fields.levelControl;
                if ( levelControl_2 )
                {
                  this_2 = (LevelRecord_o *)this;
                  this = (RetributionEffectSecondPhase_o *)levelControl_2->fields.scoreControl;
                  if ( this )
                  {
                    this = (RetributionEffectSecondPhase_o *)ScoreControl__GetLevelResultInfo((ScoreControl_o *)this, 0);
                    if ( this_2 )
                    {
                      LevelRecord__UpdateRecord(this_2, (LevelResultInfo_o *)this, 0);
                      if ( !byte_446E372 )
                      {
                        sub_1B622A0(&GameInformation_TypeInfo, method);
                        byte_446E372 = 1;
                      }
                      this = (RetributionEffectSecondPhase_o *)GameInformation_TypeInfo->static_fields->_main;
                      if ( this )
                      {
                        GameInformation__SetSongRecord(
                          (GameInformation_o *)this,
                          (System_String_o *)redFlash->fields.m_CachedPtr,
                          (System_String_o *)redFlash[2].monitor,
                          this_2,
                          0);
                        if ( !byte_446E372 )
                        {
                          sub_1B622A0(&GameInformation_TypeInfo, method);
                          byte_446E372 = 1;
                        }
                        this = (RetributionEffectSecondPhase_o *)GameInformation_TypeInfo->static_fields->_main;
                        if ( this )
                        {
                          GameInformation__UpdateRankingScore((GameInformation_o *)this, 0);
                          if ( !byte_446E372 )
                          {
                            this = (RetributionEffectSecondPhase_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
                            byte_446E372 = 1;
                          }
                          main = GameInformation_TypeInfo->static_fields->_main;
                          if ( main )
                          {
                            background = (UnityEngine_Object_o *)main->fields.background;
                            if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
                              j_il2cpp_runtime_class_init_0(v21, v22, v23);
                            UnityEngine_Object__Destroy_63300388(background, 0);
                            if ( !UnityEngine_SceneManagement_SceneManager_TypeInfo->_2.cctor_finished )
                              j_il2cpp_runtime_class_init_0(v26, v27, v28);
                            UnityEngine_SceneManagement_SceneManager__LoadScene(
                              (System_String_o *)"DFUnlock",
                              0);
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
LABEL_91:
    sub_1B624F8(this, method, method_1);
  }
  nowTime = progressControl->fields.nowTime;
  v30 = nowTime + -40.421;
  if ( (float)(nowTime + -40.421) < 0.0 )
  {
    v31 = nowTime + -21.474;
    if ( v31 <= 0.0 )
      return;
    this = (RetributionEffectSecondPhase_o *)this_1->fields.coverEffectCurve;
    if ( !this )
      goto LABEL_91;
    bgMask = (RetributionEffectSecondPhase_o *)this_1->fields.bgMask;
    v33 = UnityEngine_AnimationCurve__Evaluate((UnityEngine_AnimationCurve_o *)this, v31 / 18.947, 0);
    if ( !bgMask )
      goto LABEL_91;
    this = bgMask;
    value = v33 * 0.83;
LABEL_90:
    UnityEngine_CanvasGroup__set_alpha((UnityEngine_CanvasGroup_o *)this, value, 0);
    return;
  }
  if ( nowTime <= 101.68 )
  {
    timeAffect = this_1->fields.timeAffect;
    v39 = this_1->fields.beginHP + timeAffect * -0.5 * v30 * v30;
    v40 = this_1->fields.totalNoteAffect + v39;
    if ( !this_1->fields.dead && v40 <= 0.0 )
    {
      this_1->fields.dead = 1;
      judgeControl = levelControl->fields.judgeControl;
      if ( !judgeControl )
        goto LABEL_91;
      judgeControl->fields.stopJudge = 1;
      levelInformation = levelControl->fields.levelInformation;
      if ( !levelInformation )
        goto LABEL_91;
      levelInformation->fields.canLevelOver = 0;
      redFlash_1 = (Il2CppObject *)this_1->fields.redFlash;
      if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(*(float *)&v39, *(float *)&timeAffect, COERCE_FLOAT(COERCE_UNSIGNED_INT64(v30)));
      UnityEngine_Object__Instantiate_object__33364316(
        redFlash_1,
        (const MethodInfo_1FD195C *)Method_UnityEngine_Object_Instantiate_GameObject___);
      todo = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
      System_Action___ctor(todo, (Il2CppObject *)this_1, Method_RetributionEffectSecondPhase__Update_b__26_0__, 0);
      this = (RetributionEffectSecondPhase_o *)SimpleCoroutine__SimpleDelay(
                                                 (UnityEngine_MonoBehaviour_o *)this_1,
                                                 1.5,
                                                 todo,
                                                 0);
      levelControl_3 = this_1->fields.levelControl;
      if ( !levelControl_3 )
        goto LABEL_91;
      progressControl_2 = levelControl_3->fields.progressControl;
      if ( !progressControl_2 )
        goto LABEL_91;
      routine = RetributionEffectSecondPhase__AudioFadeOut(this, progressControl_2->fields.audioSource, 1.0, method_1);
      UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this_1, routine, 0);
    }
    v48 = 1.0;
    if ( v40 <= 1.0 )
      v48 = v40;
    else
      this_1->fields.totalNoteAffect = this_1->fields.totalNoteAffect - (v40 + -1.0);
    displayHP = this_1->fields.displayHP;
    v50 = UnityEngine_Time__get_deltaTime(0) * 6.0;
    v51 = v48;
    v52 = v50 < 0.0;
    v53 = fminf(v50, 1.0);
    if ( v52 )
      v53 = 0.0;
    v54 = displayHP + (float)((float)(v51 - displayHP) * v53);
    displayHP_2 = fminf(v54, 1.0);
    if ( v54 < 0.0 )
      displayHP_1 = 0.0;
    else
      displayHP_1 = displayHP_2;
    v57 = displayHP_1 * displayHP_1;
    this_1->fields.displayHP = displayHP_1;
    HPDisplayControl__set_HP(displayHP_1 * displayHP_1, 0);
    if ( !byte_446E86B )
    {
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      byte_446E86B = 1;
    }
    HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
    v59 = fminf(v30 / 61.263, 1.0);
    static_fields = HPDisplayControl_TypeInfo->static_fields;
    if ( (float)(v30 / 61.263) < 0.0 )
      v61 = 0.0;
    else
      v61 = v59;
    static_fields->_Opacity_k__BackingField = (float)((float)((float)(1.0 - v57) * (float)(1.0 - v57)) * 0.5) + 0.1;
    _MainColor_k__BackingField = vaddq_f32(
                                   (float32x4_t)this_1->fields.startColor,
                                   vmulq_n_f32(
                                     vsubq_f32(
                                       (float32x4_t)this_1->fields.endColor,
                                       (float32x4_t)this_1->fields.startColor),
                                     v61));
    if ( !byte_446E86C )
    {
      _MainColor_k__BackingField_1 = _MainColor_k__BackingField;
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
      byte_446E86C = 1;
      _MainColor_k__BackingField = _MainColor_k__BackingField_1;
      static_fields = HPDisplayControl_TypeInfo->static_fields;
    }
    static_fields->_MainColor_k__BackingField = (struct UnityEngine_Color_o)_MainColor_k__BackingField;
    v63 = 1.0 - (float)(v57 + v57);
    v64 = fminf(v63, 1.0) * 0.5;
    if ( v63 < 0.0 )
      a = 0.0;
    else
      a = v64;
    if ( !byte_446E86D )
    {
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
      byte_446E86D = 1;
    }
    static_fields_1 = HPDisplayControl_TypeInfo->static_fields;
    static_fields_1->_CoverColor_k__BackingField.fields.a = a;
    *(_QWORD *)&static_fields_1->_CoverColor_k__BackingField.fields.r = 0;
    static_fields_1->_CoverColor_k__BackingField.fields.b = 0.0;
    v67 = v57 * 49.0;
    if ( v57 >= 0.02 )
      v67 = 1.0 - v57;
    _GlowOpacity_k__BackingField = v67 * 0.3;
    if ( !byte_446E86E )
    {
      sub_1B622A0(&HPDisplayControl_TypeInfo, method);
      HPDisplayControl_TypeInfo = HPDisplayControl_TypeInfo;
      byte_446E86E = 1;
    }
    HPDisplayControl_TypeInfo->static_fields->_GlowOpacity_k__BackingField = _GlowOpacity_k__BackingField;
    this = (RetributionEffectSecondPhase_o *)this_1->fields.uiCanvasGroup;
    if ( !this )
      goto LABEL_91;
    v69 = (float)(v51 * 20.0) + -1.0;
    value_2 = fminf(v69, 1.0);
    v52 = v69 < 0.0;
    value_1 = 0.0;
    if ( !v52 )
      value_1 = value_2;
    UnityEngine_CanvasGroup__set_alpha((UnityEngine_CanvasGroup_o *)this, value_1, 0);
    this = (RetributionEffectSecondPhase_o *)this_1->fields.bgMask;
    if ( !this )
      goto LABEL_91;
    value = 1.0 - this_1->fields.displayHP;
    goto LABEL_90;
  }
  if ( !byte_446E86C )
  {
    sub_1B622A0(&HPDisplayControl_TypeInfo, method);
    byte_446E86C = 1;
  }
  HPDisplayControl_TypeInfo_1 = HPDisplayControl_TypeInfo;
  HPDisplayControl_TypeInfo->static_fields->_MainColor_k__BackingField = (struct UnityEngine_Color_o)MainColor_k__BackingField;
  if ( !byte_446E86B )
  {
    sub_1B622A0(&HPDisplayControl_TypeInfo, method);
    HPDisplayControl_TypeInfo_1 = HPDisplayControl_TypeInfo;
    byte_446E86B = 1;
  }
  static_fields_2 = HPDisplayControl_TypeInfo_1->static_fields;
  static_fields_2->_Opacity_k__BackingField = 0.5;
  if ( !byte_446E86F )
  {
    sub_1B622A0(&HPDisplayControl_TypeInfo, method);
    byte_446E86F = 1;
    static_fields_2 = HPDisplayControl_TypeInfo->static_fields;
  }
  static_fields_2->_LockSlash_k__BackingField = 1;
  routine_1 = RetributionEffectSecondPhase__LockAnim(this_1, method);
  UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this_1, routine_1, 0);
  this_1->fields.locked = 1;
}
