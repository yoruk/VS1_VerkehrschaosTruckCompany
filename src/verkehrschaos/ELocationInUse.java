package verkehrschaos;


/**
* verkehrschaos/ELocationInUse.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from verkehrschaos.idl
* Freitag, 17. April 2015 12:12 Uhr MESZ
*/

public final class ELocationInUse extends org.omg.CORBA.UserException
{
  public String msg = null;

  public ELocationInUse ()
  {
    super(ELocationInUseHelper.id());
  } // ctor

  public ELocationInUse (String _msg)
  {
    super(ELocationInUseHelper.id());
    msg = _msg;
  } // ctor


  public ELocationInUse (String $reason, String _msg)
  {
    super(ELocationInUseHelper.id() + "  " + $reason);
    msg = _msg;
  } // ctor

} // class ELocationInUse
