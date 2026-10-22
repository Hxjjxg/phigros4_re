void LuminescenceLevelEffect__Update(LuminescenceLevelEffect_o *this, const MethodInfo *method)
{
  UnityEngine_ParticleSystem_o *shootUpParticleSystem; // x0
  const MethodInfo *method_1; // x1
  struct ProgressControl_o *progressControl; // x8
  float nowTime; // s0
  float nowTime_1; // s0
  bool _eventStart; // w9
  float scoreCheckTime; // s1
  struct ScoreControl_o *scoreControl; // x9
  const MethodInfo *method_2; // x1
  System_Collections_IEnumerator_o *routine; // x0
  struct LevelInformation_o *levelInformation; // x9
  const MethodInfo *method_3; // x1
  System_Collections_IEnumerator_o *routine_1; // x0

  shootUpParticleSystem = (UnityEngine_ParticleSystem_o *)System_Object__Equals_55341896(
                                                            (Il2CppObject *)this->fields._mainCamera,
                                                            0,
                                                            0);
  if ( ((unsigned __int8)shootUpParticleSystem & 1) == 0 )
  {
    progressControl = this->fields._progressControl;
    if ( !progressControl
      || ((nowTime = progressControl->fields.nowTime, nowTime < this->fields._effectStopTime)
       || !this->fields._eventP1Failed && !this->fields._eventP2Failed)
      && (LuminescenceLevelEffect__UpdateEffectParam(this, nowTime, method_1),
          (progressControl = this->fields._progressControl) == 0) )
    {
LABEL_36:
      sub_1B624F8(shootUpParticleSystem, method_1);
    }
    nowTime_1 = progressControl->fields.nowTime;
    _eventStart = nowTime_1 >= this->fields.eventStartTime && nowTime_1 <= this->fields.eventEndTime;
    scoreCheckTime = this->fields.scoreCheckTime;
    this->fields._eventStart = _eventStart;
    if ( nowTime_1 >= scoreCheckTime && !this->fields._checkEnd )
    {
      scoreControl = this->fields._scoreControl;
      if ( !scoreControl )
        goto LABEL_36;
      if ( scoreControl->fields._score < this->fields._targetScore )
        this->fields._eventP2Failed = 1;
      this->fields._checkEnd = 1;
    }
    if ( !this->fields._eventP1Failed && !this->fields._eventP2Failed )
    {
      if ( nowTime_1 > this->fields.startEffectTime && !this->fields._timeTag )
      {
        shootUpParticleSystem = this->fields._shootUpParticleSystem;
        if ( !shootUpParticleSystem )
          goto LABEL_36;
        UnityEngine_ParticleSystem__Play_63632732(shootUpParticleSystem, 0);
        routine = LuminescenceLevelEffect__HideUI(this, method_2);
        shootUpParticleSystem = (UnityEngine_ParticleSystem_o *)UnityEngine_MonoBehaviour__StartCoroutine_63287500(
                                                                  (UnityEngine_MonoBehaviour_o *)this,
                                                                  routine,
                                                                  0);
        progressControl = this->fields._progressControl;
        if ( !progressControl )
          goto LABEL_36;
        levelInformation = progressControl->fields.levelInformation;
        if ( !levelInformation )
          goto LABEL_36;
        levelInformation->fields.canLevelOver = 0;
        ++this->fields._timeTag;
      }
      if ( progressControl->fields.nowTime > this->fields.explosionTime && this->fields._timeTag == 1 )
      {
        shootUpParticleSystem = this->fields._explodeParticleSystem;
        if ( !shootUpParticleSystem )
          goto LABEL_36;
        UnityEngine_ParticleSystem__Play_63632732(shootUpParticleSystem, 0);
        progressControl = this->fields._progressControl;
        ++this->fields._timeTag;
        if ( !progressControl )
          goto LABEL_36;
      }
      if ( progressControl->fields.nowTime > this->fields.stopTime && this->fields._timeTag == 2 )
      {
        shootUpParticleSystem = this->fields._shootUpParticleSystem;
        if ( !shootUpParticleSystem )
          goto LABEL_36;
        UnityEngine_ParticleSystem__Stop_63633084(shootUpParticleSystem, 0);
        shootUpParticleSystem = this->fields._explodeParticleSystem;
        if ( !shootUpParticleSystem )
          goto LABEL_36;
        UnityEngine_ParticleSystem__Stop_63633084(shootUpParticleSystem, 0);
        shootUpParticleSystem = (UnityEngine_ParticleSystem_o *)this->fields._messageController;
        if ( !shootUpParticleSystem )
          goto LABEL_36;
        Ch8Effect_MessageController__StartPopMessage((Ch8Effect_MessageController_o *)shootUpParticleSystem, 0);
        routine_1 = LuminescenceLevelEffect__UnlockRetribution(this, method_3);
        UnityEngine_MonoBehaviour__StartCoroutine_63287500((UnityEngine_MonoBehaviour_o *)this, routine_1, 0);
        ++this->fields._timeTag;
      }
    }
  }
}
