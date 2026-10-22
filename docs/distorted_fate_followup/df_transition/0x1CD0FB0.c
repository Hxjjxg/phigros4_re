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
  struct ProgressControl_o *progressControl; // x8
  struct ProgressControl_o *_progressControl; // x8
  float nowTime; // s0
  struct ProgressControl_o *_progressControl_1; // x8
  struct AddressableAudioSource_o *audioSource; // x8
  __int64 v16; // x23
  struct LevelControl_o *levelControl; // x8
  LevelRecord_o *pause_1; // x20
  __int64 pause_2; // x19
  const MethodInfo *method_2; // x2
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

  if ( (byte_446E411 & 1) == 0 ) /*0x1cd0fc8*/
  {
    sub_1B622A0(&UnityEngine_Object_TypeInfo, method); /*0x1cd0fd4*/
    sub_1B622A0(&SaveManagement_TypeInfo, v3); /*0x1cd0fe0*/
    sub_1B622A0(&"DistortedFateINSGrade", v4); /*0x1cd0fec*/
    sub_1B622A0(&"LastChapterBackgroundCode", v5); /*0x1cd0ff8*/
    sub_1B622A0(&"IN", v6); /*0x1cd1004*/
    sub_1B622A0(&"MainStory8_2BlurS", v7); /*0x1cd1010*/
    sub_1B622A0(&"QDEvent", v8); /*0x1cd101c*/
    byte_446E411 = 1; /*0x1cd1024*/
  }
  pause = System_Object__Equals_55341896((Il2CppObject *)this->fields._mainCamera, 0, 0); /*0x1cd1034*/
  if ( (pause & 1) == 0 ) /*0x1cd1038*/
  {
    progressControl = this->fields._progressControl; /*0x1cd103c*/
    if ( progressControl ) /*0x1cd1040*/
    {
      DFLevelEffect__UpdateEffectParam(this, progressControl->fields.nowTime, method_1); /*0x1cd104c*/
      _progressControl = this->fields._progressControl; /*0x1cd1050*/
      if ( _progressControl ) /*0x1cd1054*/
      {
        nowTime = _progressControl->fields.nowTime; /*0x1cd1058*/
        if ( nowTime >= 88.0 && this->fields._timeTag == -1 ) /*0x1cd1074*/
          this->fields._timeTag = 0; /*0x1cd1078*/
        if ( nowTime <= this->fields.closeCornerUITime ) /*0x1cd1084*/
          goto LABEL_70; /*0x1cd1084*/
        if ( this->fields._timeTag ) /*0x1cd1088*/
          goto LABEL_70; /*0x1cd1088*/
        pause = (__int64)this->fields._pause; /*0x1cd1090*/
        if ( pause ) /*0x1cd1094*/
        {
          UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd10a0*/
          pause = (__int64)this->fields._songsLevel; /*0x1cd10a4*/
          if ( pause ) /*0x1cd10a8*/
          {
            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd10b4*/
            pause = (__int64)this->fields._score; /*0x1cd10b8*/
            if ( pause ) /*0x1cd10bc*/
            {
              UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd10c8*/
              pause = (__int64)this->fields._progressBar; /*0x1cd10cc*/
              if ( pause ) /*0x1cd10d0*/
              {
                UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd10dc*/
                pause = (__int64)this->fields._songsName; /*0x1cd10e0*/
                if ( pause ) /*0x1cd10e4*/
                {
                  UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd10f0*/
                  _progressControl = this->fields._progressControl; /*0x1cd10f8*/
                  ++this->fields._timeTag; /*0x1cd1100*/
                  if ( _progressControl ) /*0x1cd1104*/
                  {
LABEL_70:
                    if ( _progressControl->fields.nowTime <= this->fields.openCountDownTime /*0x1cd1148*/
                      || this->fields._timeTag != 1
                      || (pause = (__int64)this->fields.countDown) != 0
                      && (UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 1, 0),
                          _progressControl = this->fields._progressControl,
                          ++this->fields._timeTag,
                          _progressControl) )
                    {
                      if ( _progressControl->fields.nowTime <= this->fields.closeAllUITime || this->fields._timeTag != 2 ) /*0x1cd1164*/
                        return; /*0x1cd1164*/
                      pause = (__int64)this->fields.countDown; /*0x1cd1168*/
                      if ( pause ) /*0x1cd116c*/
                      {
                        UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd1178*/
                        pause = (__int64)this->fields._combo; /*0x1cd117c*/
                        if ( pause ) /*0x1cd1180*/
                        {
                          UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd118c*/
                          pause = (__int64)this->fields._comboText; /*0x1cd1190*/
                          if ( pause ) /*0x1cd1194*/
                          {
                            UnityEngine_GameObject__SetActive((UnityEngine_GameObject_o *)pause, 0, 0); /*0x1cd11a0*/
                            _progressControl_1 = this->fields._progressControl; /*0x1cd11a4*/
                            if ( _progressControl_1 ) /*0x1cd11a8*/
                            {
                              audioSource = _progressControl_1->fields.audioSource; /*0x1cd11ac*/
                              if ( audioSource ) /*0x1cd11b0*/
                              {
                                pause = (__int64)audioSource->fields.audioSource; /*0x1cd11b4*/
                                if ( pause ) /*0x1cd11b8*/
                                {
                                  if ( UnityEngine_AudioSource__get_isPlaying((UnityEngine_AudioSource_o *)pause, 0) ) /*0x1cd11c0*/
                                    return; /*0x1cd11c4*/
                                  ++this->fields._timeTag; /*0x1cd11e4*/
                                  if ( !byte_446E372 ) /*0x1cd11ec*/
                                  {
                                    sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cd11f8*/
                                    byte_446E372 = 1; /*0x1cd1200*/
                                  }
                                  pause = (__int64)GameInformation_TypeInfo->static_fields->_main; /*0x1cd1214*/
                                  if ( pause ) /*0x1cd1218*/
                                  {
                                    v16 = *(_QWORD *)(pause + 48); /*0x1cd121c*/
                                    if ( v16 ) /*0x1cd1220*/
                                    {
                                      pause = (__int64)GameInformation__GetSongRecord( /*0x1cd1230*/
                                                         (GameInformation_o *)pause,
                                                         *(System_String_o **)(v16 + 16),
                                                         *(System_String_o **)(v16 + 56),
                                                         0);
                                      levelControl = this->fields._levelControl; /*0x1cd1234*/
                                      if ( levelControl ) /*0x1cd1238*/
                                      {
                                        pause_1 = (LevelRecord_o *)pause; /*0x1cd123c*/
                                        pause = (__int64)levelControl->fields.scoreControl; /*0x1cd1240*/
                                        if ( pause ) /*0x1cd1244*/
                                        {
                                          pause = (__int64)ScoreControl__GetLevelResultInfo((ScoreControl_o *)pause, 0); /*0x1cd124c*/
                                          if ( pause_1 ) /*0x1cd1250*/
                                          {
                                            pause_2 = pause; /*0x1cd1254*/
                                            LevelRecord__UpdateRecord(pause_1, (LevelResultInfo_o *)pause, 0); /*0x1cd1264*/
                                            if ( !byte_446E372 ) /*0x1cd126c*/
                                            {
                                              sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cd1278*/
                                              byte_446E372 = 1; /*0x1cd1280*/
                                            }
                                            pause = (__int64)GameInformation_TypeInfo->static_fields->_main; /*0x1cd128c*/
                                            if ( pause ) /*0x1cd1290*/
                                            {
                                              GameInformation__SetSongRecord( /*0x1cd12a4*/
                                                (GameInformation_o *)pause,
                                                *(System_String_o **)(v16 + 16),
                                                *(System_String_o **)(v16 + 56),
                                                pause_1,
                                                0);
                                              if ( !byte_446E372 ) /*0x1cd12ac*/
                                              {
                                                sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cd12b8*/
                                                byte_446E372 = 1; /*0x1cd12c0*/
                                              }
                                              pause = (__int64)GameInformation_TypeInfo->static_fields->_main; /*0x1cd12cc*/
                                              if ( pause ) /*0x1cd12d0*/
                                              {
                                                GameInformation__UpdateRankingScore((GameInformation_o *)pause, 0); /*0x1cd12d8*/
                                                pause = System_String__op_Equality( /*0x1cd12f0*/
                                                          *(System_String_o **)(v16 + 56),
                                                          (System_String_o *)"IN",
                                                          0);
                                                if ( (pause & 1) != 0 ) /*0x1cd12f4*/
                                                {
                                                  if ( !pause_2 ) /*0x1cd12f8*/
                                                    goto LABEL_67; /*0x1cd12f8*/
                                                  if ( *(int *)(pause_2 + 16) >= 920000 ) /*0x1cd130c*/
                                                  {
                                                    if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cd131c*/
                                                      j_il2cpp_runtime_class_init_0(v21, v22, v23); /*0x1cd1324*/
                                                    SaveManagement__SaveBool( /*0x1cd1338*/
                                                      (System_String_o *)"DistortedFateINSGrade",
                                                      1,
                                                      method_2);
                                                  }
                                                }
                                                if ( !byte_446E372 ) /*0x1cd1340*/
                                                {
                                                  pause = sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cd134c*/
                                                  byte_446E372 = 1; /*0x1cd1354*/
                                                }
                                                main = GameInformation_TypeInfo->static_fields->_main; /*0x1cd1360*/
                                                if ( main ) /*0x1cd1364*/
                                                {
                                                  background = (UnityEngine_Object_o *)main->fields.background; /*0x1cd1370*/
                                                  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1cd1378*/
                                                    j_il2cpp_runtime_class_init_0(v21, v22, v23); /*0x1cd1380*/
                                                  pause = UnityEngine_Object__op_Implicit(background, 0); /*0x1cd138c*/
                                                  if ( (pause & 1) == 0 ) /*0x1cd1390*/
                                                    goto LABEL_64; /*0x1cd1390*/
                                                  if ( !byte_446E372 ) /*0x1cd1398*/
                                                  {
                                                    pause = sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cd13a4*/
                                                    byte_446E372 = 1; /*0x1cd13ac*/
                                                  }
                                                  _main = GameInformation_TypeInfo->static_fields->_main; /*0x1cd13b8*/
                                                  if ( _main ) /*0x1cd13bc*/
                                                  {
                                                    background_1 = (UnityEngine_Object_o *)_main->fields.background; /*0x1cd13c4*/
                                                    if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1cd13c8*/
                                                      j_il2cpp_runtime_class_init_0(v26, v27, v28); /*0x1cd13d0*/
                                                    UnityEngine_Object__Destroy_63300388(background_1, 0); /*0x1cd13dc*/
                                                    if ( !byte_446E372 ) /*0x1cd13e4*/
                                                    {
                                                      pause = sub_1B622A0(&GameInformation_TypeInfo, method_1); /*0x1cd13f0*/
                                                      byte_446E372 = 1; /*0x1cd13f8*/
                                                    }
                                                    _main_1 = GameInformation_TypeInfo->static_fields->_main; /*0x1cd1404*/
                                                    if ( _main_1 ) /*0x1cd1408*/
                                                    {
                                                      _main_1->fields.background = 0; /*0x1cd140c*/
LABEL_64:
                                                      value = IdBuilder__BuildBackgroundImageId( /*0x1cd1410*/
                                                                (System_String_o *)"MainStory8_2BlurS",
                                                                0);
                                                      if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1cd1434*/
                                                        j_il2cpp_runtime_class_init_0(v34, v35, v36); /*0x1cd1440*/
                                                      SaveManagement__SaveString( /*0x1cd1454*/
                                                        (System_String_o *)"LastChapterBackgroundCode",
                                                        value,
                                                        method_3);
                                                      BlackScreen__Show( /*0x1cd1474*/
                                                        (System_String_o *)"QDEvent",
                                                        4.0,
                                                        method_4);
                                                      return; /*0x1cd1474*/
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
    sub_1B624F8(pause, method_1); /*0x1cd1478*/
  }
}