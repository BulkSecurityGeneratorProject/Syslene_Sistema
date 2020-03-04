# Syslene
   
Os analistas da FUNASA(Fundação Nacional de Saúde e Engenharia) têm a prática de se dirigir às áreas mais remotas e necessitadas das residências, feito isso, eles começam a fazer o levantamento das informações de cada residência (Possui caixa d'água ? Possui acesso ao saneamento básico ? Possui pia na cozinha ? etc).

O levantamento das informações era feito de maneira manual, com o risco de perder as informações levantadas e a análise era muito onerosa, tendo em vista que era necessário analisar documento por documento. O syslene surgiu com o propósito de agilizar o processo de levantamento dessasinformações.

O sistema é composto pelo aplicativo, API e webapp. Após o cadastro da residência, o gestor já possui total acesso às informações dela, através do sistema.

O aplicativo foi desenvolvido com Ionic, conta com geolocalização e a interface foi pensada 100% no analista, levando em consideração que eles já são pessoas com idade mais avançada, a aplicação é baseada em botões grandes e cores destaques.

O sistema foi desenvolvido com o framework Angular 2+ e conta com um filtro robusto na tela inicial, a autenticação foi implementada com o JWT.

A API foi desenvolvida em Java, utilizando o framework Spring, a base de dados é Postgres e o modo de deploy foi realizado com Docker. 

# Executando
    - Baixe uma cópia no seu computador
    - Inicialize a API (./mvnw)
    - Inicialize o Frontend (npm start)
