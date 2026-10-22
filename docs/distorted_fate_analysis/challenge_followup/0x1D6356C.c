void GameProgressSaveModule___Apply_g__SaveGameProgressKeys_36_0(
        GameProgressSaveModule_o *this,
        const MethodInfo *method)
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
  __int64 StringLiteral_1230; // x28
  __int64 StringLiteral_1263; // x29
  _BOOL4 isFirstRun; // w27
  System_String_o *StringLiteral_5716; // x20
  System_String_o *StringLiteral_1263_1; // x1

  if ( (byte_446E987 & 1) == 0 ) /*0x1d635b0*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1d635bc*/
    sub_1B622A0(&StringLiteral_5716, v6); /*0x1d635c8*/
    sub_1B622A0(&StringLiteral_4780, v7); /*0x1d635d4*/
    sub_1B622A0(&StringLiteral_12716, v8); /*0x1d635e0*/
    sub_1B622A0(&StringLiteral_3141, v9); /*0x1d635ec*/
    sub_1B622A0(&StringLiteral_2005, v10); /*0x1d635f8*/
    sub_1B622A0(&StringLiteral_14682, v11); /*0x1d63604*/
    sub_1B622A0(&StringLiteral_2004, v12); /*0x1d63610*/
    sub_1B622A0(&StringLiteral_1263, v13); /*0x1d6361c*/
    sub_1B622A0(&StringLiteral_1230, v14); /*0x1d63628*/
    byte_446E987 = 1; /*0x1d63630*/
  }
  StringLiteral_1230 = StringLiteral_1230; /*0x1d63650*/
  StringLiteral_1263 = StringLiteral_1263; /*0x1d63654*/
  isFirstRun = this->fields.isFirstRun; /*0x1d63664*/
  StringLiteral_5716 = (System_String_o *)StringLiteral_5716; /*0x1d63668*/
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d6366c*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d63680*/
  if ( isFirstRun ) /*0x1d63688*/
    StringLiteral_1263_1 = (System_String_o *)StringLiteral_1263; /*0x1d63688*/
  else
    StringLiteral_1263_1 = (System_String_o *)StringLiteral_1230; /*0x1d63688*/
  SaveManagement__SaveString(StringLiteral_5716, StringLiteral_1263_1, 0); /*0x1d63694*/
  SaveManagement__SaveBool((System_String_o *)StringLiteral_12716, this->fields.legacyChapterFinished, 0); /*0x1d636a4*/
  SaveManagement__SaveString((System_String_o *)StringLiteral_4780, this->fields.completed, 0); /*0x1d636b4*/
  SaveManagement__SaveInt((System_String_o *)StringLiteral_14682, this->fields.songsUpdateInfo, 0); /*0x1d636c4*/
  SaveManagement__SaveInt((System_String_o *)StringLiteral_3141, this->fields.challengeModeRank, 0); /*0x1d636d4*/
  SaveManagement__SaveBool((System_String_o *)StringLiteral_2004, this->fields.alreadyShowCollectionTip, 0); /*0x1d636e4*/
  SaveManagement__SaveBool((System_String_o *)StringLiteral_2005, this->fields.alreadyShowAutoUnlockINTip, 0); /*0x1d6370c*/
}
