# Proposta Inicial do Projeto
Nesta UC iremos realizar uma Aplicação Mobile em conjunto com todas as outras UCs deste semestre. A aplicação não tem quaisquer limitações em termos do que podemos fazer. 
Tivemos a ideia de criar uma aplicação que use a **localização do utilizador** para recomendar locais de interesse para sair á noite ou ter atividades de lazer com amigos e familia, ie. bares, discotecas, cafés com esplanada, bares karaoke... E estes locais vão ser recomendados consoante a **proximidade** da localização do utilizador e as **preferências** que o utilizador insira na aplicação. Se ele quiser encontrar bares de Karaoke perto de si basta colocar que quer procurar bares de karaoke, dizer o seu "range" de distância até onde procurar e a aplicação fará o resto


### Nome da App

A aplicação tem o nome de **Go Drink**


### Publico-alvo e possíveis ideias parecidas no mercado

O nosso **publico-alvo** são desde jovens dos **18 anos** até adultos entre os **35-40 anos**
Acreditamos que a usabilidade no dia a dia de uma aplicação como a que vamos criar abrange um publico-alvo maior porque os seus objetivos são bastante interligados com qualquer faixa etária e género

Após procurar pela internet percebemos que não existe nada assim disponível no mercado. O mais parecido com esta ideia são alguns sites que consoante a localização do utilizador dão eventos na sua cidade. Não usam localização exata e apenas mostram eventos que irão acontecer perto do utilizador

## Casos de utilização

1. **Objetivo Core**:
O utilizador irá abrir a app no seu dispositivo, de seguida ao iniciar irá preencher os dados de login (nome, email e password). Quando terminar, os seus dados ficam guardados na nossa base de dados.De seguida, abre a **Home Page** onde o utilizador vai poder clicar em **Começar** para de seguida abrir uma janela que irá pedir ao utilizador para indicar algumas das suas preferências ao sair á noite. Ie. bares com esplanada, bares karaoke, discoteca de funk, discoteca de techno, discoteca ao ar livre... depois de escolher **3 a 5 destes**, a app irá pedir também até que **distância da localização** do utilizador ele quer que mostremos locais correspondentes ás suas preferências. 
De seguida abre a **Main View** onde irão ser disponibilizados os locais para a escolha do utilizador.
Após a escolha de um deles, o utilizador irá poder dentro da app **iniciar o trajeto** até ao estabelecimento de qualquer modo que ele pretenda.

2. **Segundo caso**
O utilizador irá abrir a app no seu dispositivo, de seguida ao iniciar irá preencher os dados de login (nome, email e password). Quando terminar, os seus dados ficam guardados na nossa base de dados.De seguida, abre a **Home Page** onde o utilizador vai poder clicar em **Começar** para de seguida abrir uma janela que irá pedir ao utilizador para indicar algumas das suas preferências ao sair á noite. Ie. bares com esplanada, bares karaoke, discoteca de funk, discoteca de techno, discoteca ao ar livre... depois de escolher **3 a 5 destes**, a app irá pedir também até que **distância da localização** do utilizador ele quer que mostremos locais correspondentes ás suas preferências. 
De seguida abre a **Main View** onde irão ser disponibilizados os locais para a escolha do utilizador.
Em caso de **não existir** nenhum local perto do utilizador que corresponda aos seus interesses a app irá sugerir que alargue o  **range de procura** ou sugerir **outro local** que possa interessar o utilizador. Depois de o fazer, irá escolher um dos locais e iniciar o seu trajeto.

3. **Terceiro caso** 
O utilizador irá abrir a app no seu dispositivo, de seguida ao iniciar irá preencher os dados de login (nome, email e password). Quando terminar, os seus dados ficam guardados na nossa base de dados.De seguida, abre a **Home Page** onde o utilizador vai poder clicar em **Começar** para de seguida abrir uma janela que irá pedir ao utilizador para indicar algumas das suas preferências ao sair á noite. Ie. bares com esplanada, bares karaoke, discoteca de funk, discoteca de techno, discoteca ao ar livre... depois de escolher **3 a 5 destes**, a app irá pedir também até que **distância da localização** do utilizador ele quer que mostremos locais correspondentes ás suas preferências. 
De seguida abre a **Main View** onde irão ser disponibilizados os locais para a escolha do utilizador.
Em caso de **não existir** nenhum local perto do utilizador que corresponda aos seus interesses a app irá sugerir que alargue o  **range de procura** ou sugerir **outro local** que possa interessar o utilizador. Mesmo assim, o utilizador decide não escolher um local fora do range que tinha predefinido e clica em **sair** que irá enviar o utilizador para a **Home Page**.
