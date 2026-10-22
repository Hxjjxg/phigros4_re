void MessageLevelEffects__Update(MessageLevelEffects_o *this, const MethodInfo *method)
{
  MessageLevelEffects_o *this_1; // x19
  __int64 v3; // x1
  __int64 v4; // x1
  __int64 v5; // x1
  struct ProgressControl_o *progressControl; // x8
  float nowTime; // s8
  struct ProgressControl_o *progressControl_1; // x8
  struct AddressableAudioSource_o *audioSource; // x8
  MessageLevelEffects_o *this_2; // x19
  struct GameInformation_o *main; // x22
  float v12; // s0
  float v13; // s1
  float v14; // s2
  struct GameInformation_o *_main; // x8
  UnityEngine_Object_o *background; // x19
  float v17; // s0
  float v18; // s1
  float v19; // s2
  struct GameInformation_o *_main_1; // x8
  UnityEngine_Object_o *background_1; // x19
  struct GameInformation_o *_main_2; // x8

  this_1 = this; /*0x1d46a04*/
  if ( (byte_446E8D7 & 1) == 0 ) /*0x1d46a08*/
  {
    sub_1B622A0(&Method_UnityEngine_Component_GetComponent_ScoreControl___, method); /*0x1d46a14*/
    sub_1B622A0(&UnityEngine_Object_TypeInfo, v3); /*0x1d46a20*/
    sub_1B622A0(&UnityEngine_SceneManagement_SceneManager_TypeInfo, v4); /*0x1d46a2c*/
    this = (MessageLevelEffects_o *)sub_1B622A0(&"Deduction", v5); /*0x1d46a38*/
    byte_446E8D7 = 1; /*0x1d46a40*/
  }
  progressControl = this_1->fields.progressControl; /*0x1d46a44*/
  if ( !progressControl ) /*0x1d46a48*/
    goto LABEL_32; /*0x1d46a48*/
  nowTime = progressControl->fields.nowTime; /*0x1d46a4c*/
  MessageLevelEffects__UpdateEffects(this_1, nowTime, method); /*0x1d46a58*/
  if ( nowTime <= this_1->fields.endTime ) /*0x1d46a64*/
    return; /*0x1d46a64*/
  progressControl_1 = this_1->fields.progressControl; /*0x1d46a68*/
  if ( !progressControl_1 ) /*0x1d46a6c*/
    goto LABEL_32; /*0x1d46a6c*/
  audioSource = progressControl_1->fields.audioSource; /*0x1d46a70*/
  if ( !audioSource ) /*0x1d46a74*/
    goto LABEL_32; /*0x1d46a74*/
  this = (MessageLevelEffects_o *)audioSource->fields.audioSource; /*0x1d46a78*/
  if ( !this ) /*0x1d46a7c*/
    goto LABEL_32; /*0x1d46a7c*/
  if ( UnityEngine_AudioSource__get_isPlaying((UnityEngine_AudioSource_o *)this, 0) ) /*0x1d46a84*/
    return; /*0x1d46a88*/
  UnityEngine_Behaviour__set_enabled((UnityEngine_Behaviour_o *)this_1, 0, 0); /*0x1d46aac*/
  this = (MessageLevelEffects_o *)this_1->fields.progressControl; /*0x1d46ab0*/
  if ( !this ) /*0x1d46ab4*/
    goto LABEL_32; /*0x1d46ab4*/
  this = (MessageLevelEffects_o *)UnityEngine_Component__GetComponent_object_( /*0x1d46ac4*/
                                    (UnityEngine_Component_o *)this,
                                    (const MethodInfo_1F46C38 *)Method_UnityEngine_Component_GetComponent_ScoreControl___);
  this_2 = this; /*0x1d46ad0*/
  if ( !byte_446E372 ) /*0x1d46ad4*/
  {
    this = (MessageLevelEffects_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d46ae0*/
    byte_446E372 = 1; /*0x1d46ae8*/
  }
  if ( !this_2 ) /*0x1d46aec*/
    goto LABEL_32; /*0x1d46aec*/
  main = GameInformation_TypeInfo->static_fields->_main; /*0x1d46b08*/
  this = (MessageLevelEffects_o *)ScoreControl__GetLevelResultInfo((ScoreControl_o *)this_2, 0); /*0x1d46b0c*/
  if ( !main ) /*0x1d46b10*/
    goto LABEL_32; /*0x1d46b10*/
  main->fields.levelResultInfo = (struct LevelResultInfo_o *)this; /*0x1d46b14*/
  ScoreControl__SetNoteCodeList((ScoreControl_o *)this_2, 0); /*0x1d46b20*/
  if ( !byte_446E372 ) /*0x1d46b28*/
  {
    this = (MessageLevelEffects_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d46b34*/
    byte_446E372 = 1; /*0x1d46b3c*/
  }
  _main = GameInformation_TypeInfo->static_fields->_main; /*0x1d46b48*/
  if ( !_main ) /*0x1d46b4c*/
    goto LABEL_32; /*0x1d46b4c*/
  background = (UnityEngine_Object_o *)_main->fields.background; /*0x1d46b58*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1d46b60*/
    j_il2cpp_runtime_class_init_0(v12, v13, v14); /*0x1d46b68*/
  this = (MessageLevelEffects_o *)UnityEngine_Object__op_Implicit(background, 0); /*0x1d46b74*/
  if ( ((unsigned __int8)this & 1) == 0 ) /*0x1d46b78*/
    goto LABEL_29; /*0x1d46b78*/
  if ( !byte_446E372 ) /*0x1d46b80*/
  {
    this = (MessageLevelEffects_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d46b8c*/
    byte_446E372 = 1; /*0x1d46b94*/
  }
  _main_1 = GameInformation_TypeInfo->static_fields->_main; /*0x1d46ba0*/
  if ( !_main_1 ) /*0x1d46ba4*/
    goto LABEL_32; /*0x1d46ba4*/
  background_1 = (UnityEngine_Object_o *)_main_1->fields.background; /*0x1d46bac*/
  if ( !UnityEngine_Object_TypeInfo->_2.cctor_finished ) /*0x1d46bb0*/
    j_il2cpp_runtime_class_init_0(v17, v18, v19); /*0x1d46bb8*/
  UnityEngine_Object__Destroy_63300388(background_1, 0); /*0x1d46bc4*/
  if ( !byte_446E372 ) /*0x1d46bcc*/
  {
    this = (MessageLevelEffects_o *)sub_1B622A0(&GameInformation_TypeInfo, method); /*0x1d46bd8*/
    byte_446E372 = 1; /*0x1d46be0*/
  }
  _main_2 = GameInformation_TypeInfo->static_fields->_main; /*0x1d46bec*/
  if ( !_main_2 ) /*0x1d46bf0*/
LABEL_32:
    sub_1B624F8(this, method); /*0x1d46c34*/
  _main_2->fields.background = 0; /*0x1d46bf4*/
LABEL_29:
  if ( !UnityEngine_SceneManagement_SceneManager_TypeInfo->_2.cctor_finished ) /*0x1d46c04*/
    j_il2cpp_runtime_class_init_0(v17, v18, v19); /*0x1d46c0c*/
  UnityEngine_SceneManagement_SceneManager__LoadScene((System_String_o *)"Deduction", 0); /*0x1d46c30*/
}
