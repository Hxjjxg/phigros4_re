void GameProgressSaveModule___LoadFromLocal_g__LoadGameProgressKeys_38_0(
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
  System_String_o *String; // x0

  if ( (byte_446E98F & 1) == 0 ) /*0x1d64cbc*/
  {
    sub_1B622A0(&SaveManagement_TypeInfo, method); /*0x1d64cc8*/
    sub_1B622A0(&StringLiteral_5716, v6); /*0x1d64cd4*/
    sub_1B622A0(&StringLiteral_4780, v7); /*0x1d64ce0*/
    sub_1B622A0(&StringLiteral_12716, v8); /*0x1d64cec*/
    sub_1B622A0(&StringLiteral_3141, v9); /*0x1d64cf8*/
    sub_1B622A0(&StringLiteral_2005, v10); /*0x1d64d04*/
    sub_1B622A0(&StringLiteral_14682, v11); /*0x1d64d10*/
    sub_1B622A0(&StringLiteral_2004, v12); /*0x1d64d1c*/
    sub_1B622A0(&StringLiteral_1, v13); /*0x1d64d28*/
    sub_1B622A0(&StringLiteral_1230, v14); /*0x1d64d34*/
    byte_446E98F = 1; /*0x1d64d3c*/
  }
  if ( !SaveManagement_TypeInfo->_2.cctor_finished ) /*0x1d64d7c*/
    j_il2cpp_runtime_class_init_0(v2, v3, v4); /*0x1d64d94*/
  String = SaveManagement__LoadString((System_String_o *)StringLiteral_5716, (System_String_o *)StringLiteral_1230, 0); /*0x1d64da4*/
  this->fields.isFirstRun = System_String__op_Inequality(String, (System_String_o *)StringLiteral_1230, 0); /*0x1d64db8*/
  this->fields.legacyChapterFinished = SaveManagement__LoadBool((System_String_o *)StringLiteral_12716, 0, 0); /*0x1d64dd0*/
  this->fields.completed = SaveManagement__LoadString( /*0x1d64de4*/
                             (System_String_o *)StringLiteral_4780,
                             (System_String_o *)StringLiteral_1,
                             0);
  this->fields.songsUpdateInfo = SaveManagement__LoadInt((System_String_o *)StringLiteral_14682, -1, 0); /*0x1d64df8*/
  this->fields.challengeModeRank = SaveManagement__LoadInt((System_String_o *)StringLiteral_3141, 0, 0); /*0x1d64e0c*/
  this->fields.alreadyShowCollectionTip = SaveManagement__LoadBool((System_String_o *)StringLiteral_2004, 0, 0); /*0x1d64e24*/
  this->fields.alreadyShowAutoUnlockINTip = SaveManagement__LoadBool((System_String_o *)StringLiteral_2005, 0, 0); /*0x1d64e3c*/
}
