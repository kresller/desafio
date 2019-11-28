### Objetivo do Projeto

Criar aplicação que exponha uma API RESTful de criação de usuários e carros com login.

### Histórias do Usuário

1. Como usuário, quero efetuar login com os campos login e password para receber o token e me autenticar na aplicação, é necessário validar e retornar a mensagem "Invalid login or password".

2. Sem necessidade de autenticação, quero poder listar todos os usuários cadastrados.

3. Permitir o cadastro de novos usuários, é necessário validar se o e-mail e/ou login já existe e se os campos foram devidamente preenchidos.

4. Permitir a consulta do usuário pelo seu id, deve ser retornado todas as informações do usuário.

5. Permitir que o usuário seja removido, a partir de um id.

6. Com o id do usuário, quero poder atualizar seus campos, validando se e-mail e/ou login já existe e se os campos foram devidamente preenchidos.

7. Como usuário, após efetuar o login, quero retornar minhas informações cadastrais além de data de último acesso e data de criação, é necessário validar se o Token foi passado e esta válido.

8. Como usuário logado, quero poder listar todos os meus carros.

9. Como usuário logado, quero poder cadastrar um novo carro, é necessário validar se os campos foram devidamente preenchidos e se a placa do veículo já existe.

10. Como usuário logado, quero poder apagar meu carro.

11. Como usuário logado, quero consultar um carro pelo id.

12. Como usuário logado, quero poder atualizar os dados do meu carro, é necessário validar se os campos foram devidamente preenchidos e se a placa do veículo já existe.

### Soluções

O projeto foi desenvolvido utilizando a ferramenta Maven para deploy e automatização do build.

O resultado desse build é um executável ".jar" que possuí um servidor tomcat embutido dentro da aplicação para execução e disponibilização do serviço via http.

Abaixo um resumo dos recursos utilizados e sua justificativa

Solução/Tecnologia		| Justificativa
--------- 				| ------
Maven  					| Automatizar o processo de build da aplicação.
Servidor				| Foi utilizado o Servidor Tomcat embutido dentro da aplicação.
JWT						| Foram criados Classes com Filtros específicos para capturar as autenticações e tentativas de acessos a aplicação.
JPA/Hibernate			| Foram criadas as entidades Car e User para persistir os dados do usuário no banco. Uma interface de repositório foi criada para cada entidade, com o objetivo de criar novos métodos de consultas via anotações (@Query)
Banco de Dados			| Foi utilizado o banco de dados em memória H2.
Testes					| Foram criadas 2 classes de testes, UserTest e CarTest responsáveis por subrir a aplicação e realizar os testes.

Ao efetuar executar a aplicação, os dados disponíveis nos scripts schema.sql e data.sql serão executados, desta forma, teremos uma base inicial para visualização dos dados.

### Deploy

1. Abrir o terminal do Git

2. Acessar a pasta de sua preferência para download do projeto. Ex.: workspace do eclipse.

3. Executa o comando abaixo para baixar o projeto: 
```
git clone https://github.com/kresller/desafio.git
```

4. Executa o comando abaixo para instalar a aplicação:
```
mvn install
```
Este comando executará os testes e efetuará o build da aplicação, que fica disponível dentro do diretório target.

