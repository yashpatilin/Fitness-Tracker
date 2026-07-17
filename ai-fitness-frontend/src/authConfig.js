export const authConfig  = {
  clientId: 'oauth-pikce-client',
  authorizationEndpoint: 'http://localhost:8181/realms/fitness-app/protocol/openid-connect/auth',
  tokenEndpoint: 'http://localhost:8181/realms/fitness-app/protocol/openid-connect/token',
  redirectUri: 'http://localhost:5173',
  autoLogin: false,
  scope: 'openid profile offline_access',
  onRefreshTokenExpire: (event) => event.logIn(),
}