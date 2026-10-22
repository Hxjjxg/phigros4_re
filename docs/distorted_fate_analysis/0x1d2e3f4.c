void RetributionEffect___Update_b__20_0(RetributionEffect_o *this, const MethodInfo *method)
{
  GameInformation_o *main; // x0
  struct LevelStartInfo_o *levelStartInfo; // x23
  struct LevelControl_o *levelControl; // x8
  LevelRecord_o *main_1; // x19
  struct LevelControl_o *levelControl_1; // x8
  struct LevelInformation_o *levelInformation; // x8

  if ( (byte_446E820 & 1) == 0 )
  {
    sub_1B622A0(&"321Unlock", method);
    byte_446E820 = 1;
  }
  if ( !byte_446E372 )
  {
    sub_1B622A0(&GameInformation_TypeInfo, method);
    byte_446E372 = 1;
  }
  main = GameInformation_TypeInfo->static_fields->_main;
  if ( !main )
    goto LABEL_20;
  levelStartInfo = main->fields.levelStartInfo;
  if ( !levelStartInfo )
    goto LABEL_20;
  main = (GameInformation_o *)GameInformation__GetSongRecord(
                                main,
                                levelStartInfo->fields.songsId,
                                levelStartInfo->fields.songsLevel,
                                0);
  levelControl = this->fields.levelControl;
  if ( !levelControl )
    goto LABEL_20;
  main_1 = (LevelRecord_o *)main;
  main = (GameInformation_o *)levelControl->fields.scoreControl;
  if ( !main )
    goto LABEL_20;
  main = (GameInformation_o *)ScoreControl__GetLevelResultInfo((ScoreControl_o *)main, 0);
  levelControl_1 = this->fields.levelControl;
  if ( !levelControl_1 )
    goto LABEL_20;
  levelInformation = levelControl_1->fields.levelInformation;
  if ( !levelInformation )
    goto LABEL_20;
  method = (const MethodInfo *)main;
  if ( !main )
    goto LABEL_20;
  LODWORD(main->fields.rKSLeaderBoard) = levelInformation->fields.numOfNotes
                                       - HIDWORD(main->fields.m_CancellationTokenSource)
                                       - *(_DWORD *)&main->fields.isGameServiceAvailable
                                       - *(_DWORD *)(&main->fields.ifUserLogin + 2);
  if ( !main_1 )
    goto LABEL_20;
  LevelRecord__UpdateRecord(main_1, (LevelResultInfo_o *)main, 0);
  if ( !byte_446E372 )
  {
    sub_1B622A0(&GameInformation_TypeInfo, method);
    byte_446E372 = 1;
  }
  main = GameInformation_TypeInfo->static_fields->_main;
  if ( !main )
    goto LABEL_20;
  GameInformation__SetSongRecord(main, levelStartInfo->fields.songsId, levelStartInfo->fields.songsLevel, main_1, 0);
  if ( !byte_446E372 )
  {
    sub_1B622A0(&GameInformation_TypeInfo, method);
    byte_446E372 = 1;
  }
  main = GameInformation_TypeInfo->static_fields->_main;
  if ( !main )
LABEL_20:
    sub_1B624F8(main, method);
  GameInformation__UpdateRankingScore(main, 0);
  BlackScreen__Show((System_String_o *)"321Unlock", 2.0, 0);
}
