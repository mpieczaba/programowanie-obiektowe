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
