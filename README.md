# programowanie-obiektowe

## Back-end

### Uruchamianie back-endu na potrzeby developmentu

1. Upewnij się, że masz zainstalowany [Java Development Kit](https://openjdk.org/) w wersji 17 lub nowszej oraz [Gradle](https://gradle.org/), co najmniej w wersji 8.1.1.

2. Sklonuj repozytorium na swoje urządzenie:

   ```bash
   $ git clone https://github.com/mpieczaba/programowanie-obiektowe.git
   ```

3. Przejdź do katalogu z kodem źródłowym

   ```bash
    $ cd programowanie-obiektowe
   ```

4. Utwórz plik `.env` i dodaj do niego wymagane zmienne środowiskowe:

   ```bash
   $ cp .env.example .env
   ```

5. Przejdź do katalogu z plikami źródłowymi back-endu:

   ```bash
   $ cd backend
   ```

6. Skompiluj wymagane zależności:

   ```bash
   $ ./gradlew buildDependents
   ```

7. Uruchom aplikację back-endu:

   ```bash
   $ ./gradlew run
   ```

## Front-end

### Uruchamianie front-endu na potrzeby developmentu

1. Upewnij się, że masz zainstalowany kompilator [TypeScript](https://www.typescriptlang.org/) w wersji 5.1.3 lub nowszej praz kompilator [SCSS](https://sass-lang.com/) co najmniej w wersji 1.63.4.

2. Zainstaluj lokalny testowy serwer plików, np. [live-server](https://github.com/tapio/live-server).

3. Sklonuj repozytorium na swoje urządzenie:

   ```bash
   $ git clone https://github.com/mpieczaba/programowanie-obiektowe.git
   ```

4. Przejdź do katalogu z kodem źródłowym

   ```bash
    $ cd programowanie-obiektowe/frontend
   ```

5. Zainstaluj potrzebne zależności:

   ```bash
   $ yarn install

   ```

6. Uruchom aplikację front-endu:

   ```bash
   $ yarn run dev
   ```

7. Uruchom testowy serwer plików:

   ```bash
   $ live-server ./src
   ```
