void DFLevelEffect__Update(DFLevelEffect_o *this, const MethodInfo *method)
{
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  __int64 v6; // x1
  __int64 v7; // x1
  __int64 v8; // x1
  __int64 pause; // x0
  const MethodInfo *method_1; // x1
  const MethodInfo *method_2; // x2
  struct ProgressControl_o *progressControl; // x8
  struct ProgressControl_o *_progressControl; // x8
  float nowTime; // s0
  struct ProgressControl_o *_progressControl_1; // x8
  struct AddressableAudioSource_o *audioSource; // x8
  __int64 v17; // x23
  struct LevelControl_o *levelControl; // x8
  LevelRecord_o *pause_1; // x20
  __int64 pause_2; // x19
  float v21; // s0
  float v22; // s1
  float v23; // s2
  struct GameInformation_o *main; // x8
  UnityEngine_Object_o *background; // x19
  float v26; // s0
  float v27; // s1
  float v28; // s2
  struct GameInformation_o *_main; // x8
  UnityEngine_Object_o *background_1; // x19
  struct GameInformation_o *_main_1; // x8
  const MethodInfo *method_3; // x2
  System_String_o *value; // x19
  float v34; // s0
  float v35; // s1
  float v36; // s2
  const MethodInfo *method_4; // x1

  if ( (byte_446E411 & 1) == 0 )
  {
    sub_1B622A0(&UnityEngine_Object_TypeInfo, method);
    sub_1B622A0(&SaveManagement_TypeInfo, v3);
    sub_1B622A0(&"DistortedFateINSGrade", v4);
    sub_1B622A0(&"LastChapterBackgroundCode", v5);
    sub_1B622A0(&"IN", v6);
    sub_1B622A0(&"MainStory8_2BlurS", v7);
    sub_1B622A0(&"QDEvent", v8);
    byte_446E411 = 1;
  }
  pause = System_Object__Equals_55341896((Il2CppObject *)this->fields._mainCamera, 0, 0);
  if ( (pause & 1) == 0 )
  {
    progressControl = this->fields._progressControl;
    if ( progressControl )
    {
      DFLevelEffect__UpdateEffectParam(this, progressControl->fields.nowTime, method_1);
      _progressControl = this->fields._progressControl;
      if ( _progressControl )
      {
        nowTime = _progressControl->fields.nowTime;
        if ( nowTime >= 88.0 && this->fields._timeTag == -1 )
          this->fields._timeTag = 0;
        if ( nowTime <= this->fields.closeCornerUITime )
          goto LABEL_70;
        if ( this->fields._timeTag )
          goto LABEL_70;
        pause = (__int64)this->fields._pause;
        if ( pause )
        {
          UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
          pause = (__int64)this->fields._songsLevel;
          if ( pause )
          {
            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
            pause = (__int64)this->fields._score;
            if ( pause )
            {
              UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
              pause = (__int64)this->fields._progressBar;
              if ( pause )
              {
                UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
                pause = (__int64)this->fields._songsName;
                if ( pause )
                {
                  UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
                  _progressControl = this->fields._progressControl;
                  ++this->fields._timeTag;
                  if ( _progressControl )
                  {
LABEL_70:
                    if ( _progressControl->fields.nowTime <= this->fields.openCountDownTime
                      || this->fields._timeTag != 1
                      || (pause = (__int64)this->fields.countDown) != 0
                      && (UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 1, 0),
                          _progressControl = this->fields._progressControl,
                          ++this->fields._timeTag,
                          _progressControl) )
                    {
                      if ( _progressControl->fields.nowTime <= this->fields.closeAllUITime || this->fields._timeTag != 2 )
                        return;
                      pause = (__int64)this->fields.countDown;
                      if ( pause )
                      {
                        UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
                        pause = (__int64)this->fields._combo;
                        if ( pause )
                        {
                          UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
                          pause = (__int64)this->fields._comboText;
                          if ( pause )
                          {
                            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0);
                            _progressControl_1 = this->fields._progressControl;
                            if ( _progressControl_1 )
                            {
                              audioSource = _progressControl_1->fields.audioSource;
                              if ( audioSource )
                              {
                                pause = (__int64)audioSource->fields.audioSource;
                                if ( pause )
                                {
                                  if ( UnityEngine_AudioSource__get_isPlaying((UnityEngine_AudioSource_o *)pause, 0) )
                                    return;
                                  ++this->fields._timeTag;
                                  if ( !byte_446E372 )
                                  {
                                    sub_1B622A0(&GameInformation_TypeInfo, method_1);
                                    byte_446E372 = 1;
                                  }
                                  pause = (__int64)GameInformation_TypeInfo->static_fields->_main;
                                  if ( pause )
                                  {
                                    v17 = *(_QWORD *)(pause + 48);
                                    if ( v17 )
                                    {
                                      pause = (__int64)GameInformation__GetSongRecord(
                                                         (GameInformation_o *)pause,
                                                         *(System_String_o **)(v17 + 16),
                                                         *(System_String_o **)(v17 + 56),
                                                         0);
                                      levelControl = this->fields._levelControl;
                                      if ( levelControl )
                                      {
                                        pause_1 = (LevelRecord_o *)pause;
                                        pause = (__int64)levelControl->fields.scoreControl;
                                        if ( pause )
                                        {
                                          pause = (__int64)ScoreControl__GetLevelResultInfo((ScoreControl_o *)pause, 0);
                                          if ( pause_1 )
                                          {
                                            pause_2 = pause;
                                            LevelRecord__UpdateRecord(pause_1, (LevelResultInfo_o *)pause, 0);
                                            if ( !byte_446E372 )
                                            {
                                              sub_1B622A0(&GameInformation_TypeInfo, method_1);
                                              byte_446E372 = 1;
                                            }
                                            pause = (__int64)GameInformation_TypeInfo->static_fields->_main;
                                            if ( pause )
                                            {
                                              GameInformation__SetSongRecord(
                                                (GameInformation_o *)pause,
                                                *(System_String_o **)(v17 + 16),
                                                *(System_String_o **)(v17 + 56),
                                                pause_1,
                                                0);
                                              if ( !byte_446E372 )
                                              {
                                                sub_1B622A0(&GameInformation_TypeInfo, method_1);
                                                byte_446E372 = 1;
                                              }
                                              pause = (__int64)GameInformation_TypeInfo->static_fields->_main;
                                              if ( pause )
                                              {
                                                GameInformation__UpdateRankingScore((GameInformation_o *)pause, 0);
                                                pause = System_String__op_Equality(
                                                          *(System_String_o **)(v17 + 56),
                                                          (System_String_o *)"IN",
                                                          0);
                                                if ( (pause & 1) != 0 )
                                                {
                                                  if ( !pause_2 )
                                                    goto LABEL_67;
                                                  if ( *(int *)(pause_2 + 16) >= 920000 )
                                                  {
                                                    if ( !SaveManagement_TypeInfo->_2.cctor_finished )
                                                      j_il2cpp_runtime_class_init_0(v21, v22, v23);
                                                    SaveManagement__SaveBool(
                                                      (System_String_o *)"DistortedFateINSGrade",
                                                      1,
                                                      method_2);
                                                  }
                                                }
                                                if ( !byte_446E372 )
                                                {
                                                  pause = sub_1B622A0(&GameInformation_TypeInfo, method_1);
                                                  byte_446E372 = 1;
                                                }
                                                main = GameInformation_TypeInfo->static_fields->_main;
                                                if ( main )
                                                {
                                                  background = (UnityEngine_Object_o *)main->fields.background;
                                                  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
                                                    j_il2cpp_runtime_class_init_0(v21, v22, v23);
                                                  pause = UnityEngine_Object__op_Implicit(background, 0);
                                                  if ( (pause & 1) == 0 )
                                                    goto LABEL_64;
                                                  if ( !byte_446E372 )
                                                  {
                                                    pause = sub_1B622A0(&GameInformation_TypeInfo, method_1);
                                                    byte_446E372 = 1;
                                                  }
                                                  _main = GameInformation_TypeInfo->static_fields->_main;
                                                  if ( _main )
                                                  {
                                                    background_1 = (UnityEngine_Object_o *)_main->fields.background;
                                                    if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished )
                                                      j_il2cpp_runtime_class_init_0(v26, v27, v28);
                                                    UnityEngine_Object__Destroy_63300388(background_1, 0);
                                                    if ( !byte_446E372 )
                                                    {
                                                      pause = sub_1B622A0(&GameInformation_TypeInfo, method_1);
                                                      byte_446E372 = 1;
                                                    }
                                                    _main_1 = GameInformation_TypeInfo->static_fields->_main;
                                                    if ( _main_1 )
                                                    {
                                                      _main_1->fields.background = 0;
LABEL_64:
                                                      value = IdBuilder__BuildBackgroundImageId(
                                                                (System_String_o *)"MainStory8_2BlurS",
                                                                0);
                                                      if ( !SaveManagement_TypeInfo->_2.cctor_finished )
                                                        j_il2cpp_runtime_class_init_0(v34, v35, v36);
                                                      SaveManagement__SaveString(
                                                        (System_String_o *)"LastChapterBackgroundCode",
                                                        value,
                                                        method_3);
                                                      BlackScreen__Show(
                                                        (System_String_o *)"QDEvent",
                                                        4.0,
                                                        method_4);
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
LABEL_67:
    sub_1B624F8(pause, method_1, method_2);
  }
}
