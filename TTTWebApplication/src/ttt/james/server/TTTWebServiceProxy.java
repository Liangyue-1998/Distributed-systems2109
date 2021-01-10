package ttt.james.server;

public class TTTWebServiceProxy implements ttt.james.server.TTTWebService_PortType {
  private String _endpoint = null;
  private ttt.james.server.TTTWebService_PortType tTTWebService_PortType = null;
  
  public TTTWebServiceProxy() {
    _initTTTWebServiceProxy();
  }
  
  public TTTWebServiceProxy(String endpoint) {
    _endpoint = endpoint;
    _initTTTWebServiceProxy();
  }
  
  private void _initTTTWebServiceProxy() {
    try {
      tTTWebService_PortType = (new ttt.james.server.TTTWebService_ServiceLocator()).getTTTWebServicePort();
      if (tTTWebService_PortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)tTTWebService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)tTTWebService_PortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (tTTWebService_PortType != null)
      ((javax.xml.rpc.Stub)tTTWebService_PortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public ttt.james.server.TTTWebService_PortType getTTTWebService_PortType() {
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType;
  }
  
  public java.lang.String register(java.lang.String username, java.lang.String password, java.lang.String name, java.lang.String surname) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.register(username, password, name, surname);
  }
  
  public int login(java.lang.String username, java.lang.String password) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.login(username, password);
  }
  
  public java.lang.String joinGame(int uid, int gid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.joinGame(uid, gid);
  }
  
  public java.lang.String takeSquare(int x, int y, int gid, int pid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.takeSquare(x, y, gid, pid);
  }
  
  public java.lang.String getBoard(int gid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.getBoard(gid);
  }
  
  public java.lang.String getGameState(int gid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.getGameState(gid);
  }
  
  public java.lang.String showMyOpenGames(int uid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.showMyOpenGames(uid);
  }
  
  public java.lang.String setGameState(int gid, int gstate) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.setGameState(gid, gstate);
  }
  
  public java.lang.String newGame(int uid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.newGame(uid);
  }
  
  public java.lang.String checkSquare(int x, int y, int gid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.checkSquare(x, y, gid);
  }
  
  public java.lang.String checkWin(int gid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.checkWin(gid);
  }
  
  public java.lang.String deleteGame(int gid, int uid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.deleteGame(gid, uid);
  }
  
  public java.lang.String showAllMyGames(int uid) throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.showAllMyGames(uid);
  }
  
  public java.lang.String showOpenGames() throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.showOpenGames();
  }
  
  public java.lang.String leagueTable() throws java.rmi.RemoteException{
    if (tTTWebService_PortType == null)
      _initTTTWebServiceProxy();
    return tTTWebService_PortType.leagueTable();
  }
  
  
}