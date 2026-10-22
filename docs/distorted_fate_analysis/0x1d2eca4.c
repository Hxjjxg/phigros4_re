void RetributionEffectSecondPhase__OnJudge(
        RetributionEffectSecondPhase_o *this,
        double judgeScale,
        const MethodInfo *method)
{
  struct LevelControl_o *levelControl; // x8
  struct ProgressControl_o *progressControl; // x8

  if ( !this->fields.dead )
  {
    levelControl = this->fields.levelControl;
    if ( !levelControl || (progressControl = levelControl->fields.progressControl) == 0 )
      sub_1B624F8(this, method);
    if ( (float)(progressControl->fields.nowTime + -40.421) >= 0.0 )
      this->fields.totalNoteAffect = this->fields.totalNoteAffect + judgeScale;
  }
}
