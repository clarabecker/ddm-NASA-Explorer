# 🌌 NASA Explorer

Aplicativo Android nativo desenvolvido com **Kotlin** e **Jetpack Compose** que permite explorar o universo através das imagens oficiais da NASA.

Este projeto foi desenvolvido como parte da disciplina de Desenvolvimento para Dispositivos Móveis (DDM).

## 📱 Telas e Funcionalidades

O aplicativo consome a **API APOD (Astronomy Picture of the Day)** da NASA e conta com as seguintes funcionalidades:

* **🚀 Foto do Dia (Home):** Exibe a imagem astronômica do dia atual com título e botão para detalhes.
* **🔭 Galeria Infinita:** Lista de imagens aleatórias da NASA com **scroll infinito** (paginação automática ao chegar ao fim da lista).
* **📝 Detalhes:** Tela dedicada com imagem em alta resolução, título e explicação completa (descrição) da foto.
* **❤️ Favoritos:** Interface preparada para favoritar imagens (Lógica de persistência em desenvolvimento).

## 🛠️ Tecnologias Utilizadas

O projeto foi construído seguindo as melhores práticas de desenvolvimento Android moderno:

* **Linguagem:** [Kotlin](https://kotlinlang.org/)
* **Interface de Usuário:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material Design 3)
* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Navegação:** Navigation Compose
* **Consumo de API:** [Retrofit](https://square.github.io/retrofit/) 
* **Carregamento de Imagens:** [Coil](https://coil-kt.github.io/coil/)
* **Assincronismo:** Coroutines & Flow

## 🔧 Como Rodar o Projeto

1.  **Clone o repositório:**
    ```bash
    git clone [https://github.com/SEU_USUARIO/ddm-nasa-explorer.git](https://github.com/SEU_USUARIO/ddm-nasa-explorer.git)
    ```
2.  **Abra no Android Studio:**
    * Selecione a pasta do projeto clonado.
    * Aguarde a sincronização do Gradle.
3.  **Execute:**
    * Conecte um dispositivo físico ou inicie um emulador.
    * Clique no botão **Run** (▶️).

## 📚 API Reference

Este projeto utiliza a API pública da NASA:
* **Base URL:** `https://api.nasa.gov/`
* **Endpoint:** `planetary/apod`

## 👨‍💻 Autor

Desenvolvido por **Clara Becker e Lucas Falcade**
