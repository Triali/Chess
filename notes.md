# My notes
actor Client
participant Server
participant Services
participant DataAccess
database db

group #navy Register #white
Client->Server:[POST] /user\n { "username":"", "password":"", "email":"" }
Server->Services:register(username, password, email)
Services->DataAccess:GetUser(username)
DataAccess->db: SELECT username FROM user
DataAccess-->Services: null
Services->DataAccess:CreateUser(username, password, email)
DataAccess->db:INSERT username, password, email INTO user
Services->DataAccess:CreateAuthToken(username)
DataAccess->db:INSERT authToken, username INTO AuthToken
DataAccess-->Services:authToken
Services-->Server:authToken
Server-->Client:[200] { "username":"", "authToken":"" }
end

group #navy Clear #white
Client->Server:[DELETE] /db
Server->Services:ClearAll()
Services->DataAccess: ClearAll()
DataAccess->db:DELETE ALL Games, Users, AuthTokens
Server-->Client:[200]
end

group #navy Logout #white
Client->Server:[DELETE] /session
Server->Services: Logout()
Services->DataAccess:VerifyAuthToken(authToken)
DataAccess->db:SELECT authToken IN AuthToken
Services->DataAccess:RemoveAuthToken(authToken)
DataAccess->db:DELETE AuthToken byauthToken 

Server-->Client:authToken
end


group #navy Login #white
Client->Server:[POST] /session \n { "username":"", "password":"" }
Server->Services:Login(username, password)
Services->DataAccess:VerifyPassword(username, password)
DataAccess->db: SELECT username FROM user
DataAccess->db: SELECT password FROM user
Services->DataAccess: CreateAuthToken(username)
DataAccess->db:INSERT authToken, username INTO AuthToken
DataAccess-->Services:authToken
Services-->Server:authToken
Server-->Client:[200] { "username":"", "authToken":"" }
end


group #navy List Games #white
Client->Server:[GET] /game\n authorization: <authToken>
Server->Services:ListGames()
Services->DataAccess: VerifyAuthToken(authToken)
DataAccess->db:SELECT authToken IN AuthToken
Services->DataAccess:PrintAllGames()
DataAccess->db:SELECT ALL games IN Game
DataAccess-->Services:{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
Services-->Server:{ "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
Server-->Client:[200]\n { "games": [{"gameID": 1234, "whiteUsername":"", "blackUsername":"", "gameName:""} ]}
end

group #navy Create Game #white
Client->Server:[POST] /game\n authorization: <authToken>\n { "gameName":"" }
Server->Services:CreateGame(gameName, color)
Services->DataAccess: VerifyAuthToken(authToken)
DataAccess->db:SELECT authToken IN AuthToken
Services->DataAccess: CreateGame(gameName, color)
DataAccess->db:INSERT gameName, gameID, whiteUsername, blackUsername FROM Game
DataAccess-->Services:gameID
Services-->Server:gameID
Server-->Client:[200] { "gameID": 1234 }
end

group #navy Join Game #white
Client->Server:[PUT] /game\n authorization: <authToken>\n{ "playerColor":"WHITE/BLACK", "gameID": 1234 }
Server->Services:JoinGame(gameID,color)
Services->DataAccess: VerifyAuthToken(authToken)
DataAccess->db:SELECT authToken IN AuthToken
Services->DataAccess: AddToGame(gameID,color)
DataAccess->db:ADD color TO GameID FROM Game


Server-->Client:[200]
end