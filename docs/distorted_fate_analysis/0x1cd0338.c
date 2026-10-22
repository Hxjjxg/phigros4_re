bool Destruction321LevelEffect__DestructAnimation_d__59__MoveNext(
        Destruction321LevelEffect__DestructAnimation_d__59_o *this,
        const MethodInfo *method)
{
  Destruction321LevelEffect__DestructAnimation_d__59_o *this_1; // x19
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
  int32_t _1__state; // w8
  struct Destruction321LevelEffect_o *_4__this; // x22
  struct LevelControl_o *levelControl; // x8
  struct LevelInformation_o *levelInformation; // x8
  struct ProgressControl_o *progressControl; // x8
  struct JudgeControl_o *judgeControl; // x8
  struct AddressableAudioSource_o *audioSource; // x8
  float time; // s0
  struct ProgressControl_o *_progressControl; // x8
  struct AddressableAudioSource_o *_audioSource; // x8
  float t_5__2; // s8
  __int64 v29; // x24
  struct LevelControl_o *_levelControl; // x8
  LevelRecord_o *this_2; // x20
  Destruction321LevelEffect__DestructAnimation_d__59_o *this_3; // x19
  const MethodInfo *method_1; // x2
  float v34; // s0
  float v35; // s1
  float v36; // s2
  const MethodInfo *method_2; // x2
  const MethodInfo *method_3; // x2
  const MethodInfo *method_4; // x2
  const MethodInfo *method_5; // x2
  float v41; // s0
  float v42; // s1
  float v43; // s2
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo; // x0
  System_String_o *FirstOpenGameAfterChapter8FirstPhaseClear_k__BackingField; // x19
  const MethodInfo *method_6; // x2
  Phigros2_VisionReplay_VisionReplay_Key_c *Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1; // x0
  System_String_o *Chapter8UnlockSecondPhase_k__BackingField; // x19
  struct GameInformation_o *main; // x8
  struct GetCollectionControl_o *getCollectionControl; // x8
  System_Collections_Generic_IEnumerable_TSource__o *collectionItems; // x19
  Destruction321LevelEffect___c_c *Destruction321LevelEffect.__c_TypeInfo; // x0
  System_Func_object__bool__o *_9__59_0; // x20
  Il2CppObject *_; // x21
  System_Collections_Generic_IEnumerable_TSource__o *source; // x0
  __int64 v56; // x2
  int _2__current; // w8
  Destruction321LevelEffect__DestructAnimation_d__59_o *this_4; // x19
  unsigned int _2__current_1; // w20
  float v60; // s0
  float v61; // s1
  float v62; // s2
  Destruction321LevelEffect___c_c *Destruction321LevelEffect.__c_TypeInfo_1; // x0
  System_String_o *content; // x19
  System_String_o *title; // x20
  System_String_o *okText; // x21
  System_Action_o *_9__59_1; // x22
  Il2CppObject *__1; // x23
  struct AddressableAudioSource_o *_audioSource_1; // x8
  float timeClip_5__3; // s8
  UnityEngine_WaitForSeconds_o *__2__current; // x20

  this_1 = this;
  if ( (byte_446E40F & 1) == 0 )
  {
    sub_1B622A0(&System_Action_TypeInfo, method);
    sub_1B622A0(&Method_System_Linq_Enumerable_ToArray_CollectionItemIndex___, v3);
    sub_1B622A0(&Method_System_Linq_Enumerable_Where_CollectionItemIndex___, v4);
    sub_1B622A0(&System_Func_CollectionItemIndex__bool__TypeInfo, v5);
    sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, v6);
    sub_1B622A0(&SaveManagement_TypeInfo, v7);
    sub_1B622A0(&Method_Destruction321LevelEffect___c__DestructAnimation_b__59_0__, v8);
    sub_1B622A0(&Method_Destruction321LevelEffect___c__DestructAnimation_b__59_1__, v9);
    sub_1B622A0(&Destruction321LevelEffect___c_TypeInfo, v10);
    sub_1B622A0(&UnityEngine_WaitForSeconds_TypeInfo, v11);
    sub_1B622A0(&"IN", v12);
    sub_1B622A0(&"C8TheChariotREVIIVALUnlocked", v13);
    sub_1B622A0(&"C8RetributionUnlocked", v14);
    sub_1B622A0(&"DESTRUCTION321INSGrade", v15);
    sub_1B622A0(&"C8DESTRUCTION321Unlocked", v16);
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)sub_1B622A0(&"chapter8UnlockSecondPhase", v17);
    byte_446E40F = 1;
  }
  _1__state = this_1->fields.__1__state;
  _4__this = this_1->fields.__4__this;
  if ( _1__state == 1 )
  {
    this_1->fields.__1__state = -1;
  }
  else
  {
    if ( _1__state )
      return 0;
    this_1->fields.__1__state = -1;
    if ( !_4__this )
      goto LABEL_93;
    levelControl = _4__this->fields._levelControl;
    if ( !levelControl )
      goto LABEL_93;
    levelInformation = levelControl->fields.levelInformation;
    if ( !levelInformation )
      goto LABEL_93;
    levelInformation->fields.canLevelOver = 0;
    progressControl = _4__this->fields._progressControl;
    if ( !progressControl )
      goto LABEL_93;
    judgeControl = progressControl->fields.judgeControl;
    if ( !judgeControl )
      goto LABEL_93;
    judgeControl->fields.stopJudge = 1;
    audioSource = _4__this->fields._audioSource;
    if ( !audioSource )
      goto LABEL_93;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)audioSource->fields.audioSource;
    if ( !this )
      goto LABEL_93;
    UnityEngine_AudioSource__set_pitch((UnityEngine_AudioSource_o *)this, 0.75, 0);
    time = this_1->fields.time;
    this_1->fields._t_5__2 = 0.0;
    this_1->fields._timeClip_5__3 = time * 0.0078125;
    _progressControl = _4__this->fields._progressControl;
    if ( !_progressControl )
      goto LABEL_93;
    this_1->fields._currentTime_5__4 = _progressControl->fields.nowTime;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)_4__this->fields._audioSource;
    if ( !this )
      goto LABEL_93;
    AddressableAudioSource__Pause((AddressableAudioSource_o *)this, 0);
    _audioSource = _4__this->fields._audioSource;
    if ( !_audioSource )
      goto LABEL_93;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)_audioSource->fields.audioSource;
    _audioSource->fields.startTime = this_1->fields._currentTime_5__4;
    if ( !this )
      goto LABEL_93;
    UnityEngine_AudioSource__Play_62985964((UnityEngine_AudioSource_o *)this, 0);
  }
  t_5__2 = this_1->fields._t_5__2;
  if ( t_5__2 > this_1->fields.time )
  {
    if ( !_4__this )
      goto LABEL_93;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)_4__this->fields._audioSource;
    if ( !this )
      goto LABEL_93;
    AddressableAudioSource__Stop((AddressableAudioSource_o *)this, 0);
    if ( !byte_446E372 )
    {
      sub_1B622A0(&GameInformation_TypeInfo, method);
      byte_446E372 = 1;
    }
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)GameInformation_TypeInfo->static_fields->_main;
    if ( !this )
      goto LABEL_93;
    v29 = *(_QWORD *)&this->fields._timeClip_5__3;
    if ( !v29 )
      goto LABEL_93;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)GameInformation__GetSongRecord(
                                                                     (GameInformation_o *)this,
                                                                     *(System_String_o **)(v29 + 16),
                                                                     *(System_String_o **)(v29 + 56),
                                                                     0);
    _levelControl = _4__this->fields._levelControl;
    if ( !_levelControl )
      goto LABEL_93;
    this_2 = (LevelRecord_o *)this;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)_levelControl->fields.scoreControl;
    if ( !this )
      goto LABEL_93;
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)ScoreControl__GetLevelResultInfo(
                                                                     (ScoreControl_o *)this,
                                                                     0);
    if ( !this_2 )
      goto LABEL_93;
    this_3 = this;
    LevelRecord__UpdateRecord(this_2, (LevelResultInfo_o *)this, 0);
    if ( !byte_446E372 )
    {
      sub_1B622A0(&GameInformation_TypeInfo, method);
      byte_446E372 = 1;
    }
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)GameInformation_TypeInfo->static_fields->_main;
    if ( !this )
      goto LABEL_93;
    GameInformation__SetSongRecord(
      (GameInformation_o *)this,
      *(System_String_o **)(v29 + 16),
      *(System_String_o **)(v29 + 56),
      this_2,
      0);
    if ( !byte_446E372 )
    {
      sub_1B622A0(&GameInformation_TypeInfo, method);
      byte_446E372 = 1;
    }
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)GameInformation_TypeInfo->static_fields->_main;
    if ( !this )
      goto LABEL_93;
    GameInformation__UpdateRankingScore((GameInformation_o *)this, 0);
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)System_String__op_Equality(
                                                                     *(System_String_o **)(v29 + 56),
                                                                     (System_String_o *)"IN",
                                                                     0);
    if ( ((unsigned __int8)this & 1) != 0 )
    {
      if ( !this_3 )
        goto LABEL_93;
      if ( this_3->fields.__1__state >= 920000 )
      {
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v34, v35, v36);
        SaveManagement__SaveBool((System_String_o *)"DESTRUCTION321INSGrade", 1, method_1);
      }
    }
    if ( !SaveManagement_TypeInfo->_2.cctor_finished )
      j_il2cpp_runtime_class_init_0(v34, v35, v36);
    SaveManagement__SaveBool((System_String_o *)"C8TheChariotREVIIVALUnlocked", 1, method_1);
    SaveManagement__SaveBool((System_String_o *)"C8RetributionUnlocked", 1, method_2);
    SaveManagement__SaveBool((System_String_o *)"C8DESTRUCTION321Unlocked", 1, method_3);
    SaveManagement__SaveBool((System_String_o *)"chapter8UnlockSecondPhase", 1, method_4);
    if ( !byte_446E376 )
    {
      this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)sub_1B622A0(
                                                                       &Phigros2_VisionReplay_VisionReplay_TypeInfo,
                                                                       method);
      byte_446E376 = 1;
    }
    if ( Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields->_IsOn_k__BackingField )
    {
      if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v41, v42, v43);
      if ( !byte_446E413 )
      {
        sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, method);
        byte_446E413 = 1;
      }
      Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
      if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
      {
        j_il2cpp_runtime_class_init_0(v41, v42, v43);
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
      }
      FirstOpenGameAfterChapter8FirstPhaseClear_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo->static_fields->_FirstOpenGameAfterChapter8FirstPhaseClear_k__BackingField;
      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
        j_il2cpp_runtime_class_init_0(v41, v42, v43);
      SaveManagement__SaveBool(FirstOpenGameAfterChapter8FirstPhaseClear_k__BackingField, 1, method_5);
      if ( !byte_446E377 )
      {
        this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)sub_1B622A0(
                                                                         &Phigros2_VisionReplay_VisionReplay_TypeInfo,
                                                                         method);
        byte_446E377 = 1;
      }
      if ( Phigros2_VisionReplay_VisionReplay_TypeInfo->static_fields->_IsSimulateMode_k__BackingField )
      {
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v41, v42, v43);
        if ( !byte_446E414 )
        {
          sub_1B622A0(&Phigros2_VisionReplay_VisionReplay_Key_TypeInfo, method);
          byte_446E414 = 1;
        }
        Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        if ( !Phigros2_VisionReplay_VisionReplay_Key_TypeInfo->_2.cctor_finished )
        {
          j_il2cpp_runtime_class_init_0(v41, v42, v43);
          Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1 = Phigros2_VisionReplay_VisionReplay_Key_TypeInfo;
        }
        Chapter8UnlockSecondPhase_k__BackingField = Phigros2.VisionReplay.VisionReplay.Key_TypeInfo_1->static_fields->_Chapter8UnlockSecondPhase_k__BackingField;
        if ( !SaveManagement_TypeInfo->_2.cctor_finished )
          j_il2cpp_runtime_class_init_0(v41, v42, v43);
        SaveManagement__SaveBool(Chapter8UnlockSecondPhase_k__BackingField, 1, method_6);
      }
    }
    if ( !byte_446E372 )
    {
      this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)sub_1B622A0(&GameInformation_TypeInfo, method);
      byte_446E372 = 1;
    }
    main = GameInformation_TypeInfo->static_fields->_main;
    if ( !main )
      goto LABEL_93;
    getCollectionControl = main->fields.getCollectionControl;
    if ( !getCollectionControl )
      goto LABEL_93;
    collectionItems = (System_Collections_Generic_IEnumerable_TSource__o *)getCollectionControl->fields.collectionItems;
    Destruction321LevelEffect.__c_TypeInfo = Destruction321LevelEffect___c_TypeInfo;
    if ( !Destruction321LevelEffect___c_TypeInfo->_2.cctor_finished )
    {
      j_il2cpp_runtime_class_init_0(v41, v42, v43);
      Destruction321LevelEffect.__c_TypeInfo = Destruction321LevelEffect___c_TypeInfo;
    }
    _9__59_0 = (System_Func_object__bool__o *)Destruction321LevelEffect.__c_TypeInfo->static_fields->__9__59_0;
    if ( !_9__59_0 )
    {
      if ( !Destruction321LevelEffect.__c_TypeInfo->_2.cctor_finished )
      {
        j_il2cpp_runtime_class_init_0(v41, v42, v43);
        Destruction321LevelEffect.__c_TypeInfo = Destruction321LevelEffect___c_TypeInfo;
      }
      _ = (Il2CppObject *)Destruction321LevelEffect.__c_TypeInfo->static_fields->__9;
      _9__59_0 = (System_Func_object__bool__o *)sub_1B624E8(System_Func_CollectionItemIndex__bool__TypeInfo);
      System_Func_object__bool____ctor(
        _9__59_0,
        _,
        Method_Destruction321LevelEffect___c__DestructAnimation_b__59_0__,
        0);
      Destruction321LevelEffect___c_TypeInfo->static_fields->__9__59_0 = (struct System_Func_CollectionItemIndex__bool__o *)_9__59_0;
    }
    source = System_Linq_Enumerable__Where_object_(
               collectionItems,
               (System_Func_TSource__bool__o *)_9__59_0,
               (const MethodInfo_1F7E054 *)Method_System_Linq_Enumerable_Where_CollectionItemIndex___);
    this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)System_Linq_Enumerable__ToArray_object_(
                                                                     source,
                                                                     (const MethodInfo_1F7ABAC *)Method_System_Linq_Enumerable_ToArray_CollectionItemIndex___);
    if ( !this )
LABEL_93:
      sub_1B624F8(this, method);
    _2__current = (int)this->fields.__2__current;
    this_4 = this;
    if ( _2__current >= 1 )
    {
      _2__current_1 = 0;
      do
      {
        if ( _2__current_1 >= _2__current )
          sub_1B62500(this, method, v56);
        this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)*((_QWORD *)&this_4->fields.__4__this
                                                                       + (int)_2__current_1);
        if ( !this )
          goto LABEL_93;
        GetableItem__Get((GetableItem_o *)this, 0);
        _2__current = (int)this_4->fields.__2__current;
      }
      while ( (int)++_2__current_1 < _2__current );
    }
    UnityEngine_PlayerPrefs__Save(0);
    Destruction321LevelEffect.__c_TypeInfo_1 = Destruction321LevelEffect___c_TypeInfo;
    title = _4__this->fields._title;
    content = _4__this->fields._content;
    okText = _4__this->fields._okText;
    if ( !Destruction321LevelEffect___c_TypeInfo->_2.cctor_finished )
    {
      j_il2cpp_runtime_class_init_0(v60, v61, v62);
      Destruction321LevelEffect.__c_TypeInfo_1 = Destruction321LevelEffect___c_TypeInfo;
    }
    _9__59_1 = Destruction321LevelEffect.__c_TypeInfo_1->static_fields->__9__59_1;
    if ( !_9__59_1 )
    {
      if ( !Destruction321LevelEffect.__c_TypeInfo_1->_2.cctor_finished )
      {
        j_il2cpp_runtime_class_init_0(v60, v61, v62);
        Destruction321LevelEffect.__c_TypeInfo_1 = Destruction321LevelEffect___c_TypeInfo;
      }
      __1 = (Il2CppObject *)Destruction321LevelEffect.__c_TypeInfo_1->static_fields->__9;
      _9__59_1 = (System_Action_o *)sub_1B624E8(System_Action_TypeInfo);
      System_Action___ctor(_9__59_1, __1, Method_Destruction321LevelEffect___c__DestructAnimation_b__59_1__, 0);
      Destruction321LevelEffect___c_TypeInfo->static_fields->__9__59_1 = _9__59_1;
    }
    pingak9_NativeDialog__OpenDialog(title, content, okText, _9__59_1, 0);
    return 0;
  }
  this_1->fields._t_5__2 = t_5__2 + (float)(UnityEngine_Time__get_deltaTime(0) + this_1->fields._timeClip_5__3);
  if ( !_4__this )
    goto LABEL_93;
  _audioSource_1 = _4__this->fields._audioSource;
  if ( !_audioSource_1 )
    goto LABEL_93;
  this = (Destruction321LevelEffect__DestructAnimation_d__59_o *)_audioSource_1->fields.audioSource;
  if ( !this )
    goto LABEL_93;
  UnityEngine_AudioSource__set_time((UnityEngine_AudioSource_o *)this, this_1->fields._currentTime_5__4, 0);
  timeClip_5__3 = this_1->fields._timeClip_5__3;
  __2__current = (UnityEngine_WaitForSeconds_o *)sub_1B624E8(UnityEngine_WaitForSeconds_TypeInfo);
  UnityEngine_WaitForSeconds___ctor(__2__current, timeClip_5__3, 0);
  this_1->fields.__2__current = (Il2CppObject *)__2__current;
  this_1->fields.__1__state = 1;
  return 1;
}
