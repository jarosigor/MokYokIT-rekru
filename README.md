
# MokYokIT Rekrutacja

## Task 1
Zadanie polegało na odpowiednim przetworzeniu pliku JSON oraz jego zapisie do pliku CSV.
Do przetworzenia wejściowego pliku JSON zdecydowałem się skorzystać z biblioteki [json-smart](https://mvnrepository.com/artifact/net.minidev/json-smart), która znacząco ułatwia to zadanie. ArrayList'e z **JSONObject**'ami sortuję odpowiednio do założeń zadania, po czym obsługuję funkcję zapisu do pliku CSV, gdzie jako separator użyłem znaku **";"**. W projekcie jako build tool korzystam z **Gradle**.

## Task 2
Treść zadania: 
> Zaproponuj zapytanie SQL zwracające ostatni status każdego klienta, z którym były co najmniej 3 próby kontaktu. Potraktuj podesłany plik jako tabelę.
Przy pomocy "window functions" (**ROW_NUMBER() OVER..** oraz **COUNT() OVER..**) otrzymuję kolejność każdego statusu kontaktu oraz podliczoną liczbę prób kontaktu. Następnie filtruję dane zgodnie z założeniami.


## Task 3
W tym zadaniu ponownie korzystam z "window functions" (**LAG() OVER..**), dzięki której otrzymuję status przed finalnym statusem. Zostało sprecyzowane iż nasz fakt posiada 1 wymiar (data) i nie może być timestamp'em, stąd wykorzystanie funkcji **TRUNC()**. Dalej odpowiednio do założeń dodaję miary, gdzie przy "bonusowych" wykorzystuję wcześniej przygotowany **last_status**. Rezultat grupuję po dacie.

## Task 4 
Wykorzystując nowe narzędzie ponownie rozwiązałem Task1. Jak zostało zasugerowane w zadaniu użyłem bloczki tFileInputJSON, tFilterRow, tSortRow i tFileOutputDelimited. W tFileInputJSON utworzyłem odpowiedni schemat danych oraz zdefiniowałem ich mapowanie. Przy tFilterRow zastosowałem opcję trybu zawansowanego by przefiltrować dane wejściowe.
`TalendDate.compareDate(input_row.kontakt_ts, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-06-30 23:59:59"), "yyyy-MM-dd HH:mm:ss") > 0`. Dane odpowiednio sortuję i przekazuję do pliku CSV.
(Nie jestem pewnien czy projekt odpowiednio wyeksportowałem do gh, w razie pomyłki proszę o informację postaram się jak najszybciej to poprawić)
