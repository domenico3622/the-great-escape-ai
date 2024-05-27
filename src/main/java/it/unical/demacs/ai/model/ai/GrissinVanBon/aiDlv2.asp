%atomiPerInput.txt%-----------FATTI IN INPUT-------------
% wall(R1,C1,R2,C2, R3,C3,R4,C4). indica che c'è un muro tra le celle (R1,R2)(R2,C2) e un muro tra (R3,C3)(R4,C4). ogni muro occupa due celle
% player(ID,R,C,W,D). indica che il giocatore ID è in posizione (R,C), ha W muri a disposizione e la direzione di vittoria è D
% D è una stringa che può essere "N", "S", "E", "W" e indica la direzione di vittoria del giocatore
% me(ID). indica noi
% maxWall(10). indica il numero massimo di muri che si possono piazzare
% numPlayers(4). indica il numero di giocatori
% costLimitPaths(N).  stima per il costoLimite dei cammini minimi, per ottimizzare.
% minDistanceInitial(IdPlayer, DistanceWin). indica la distanza minima del giocatore IdPlayer per vincere

%-----------FATTI DA RESTITUIRE------------
% newWall(R1,C1,R2,C2, R3,C3,R4,C4). indica che vogliamo piazzare un muro tra le celle (R1,R2)(R2,C2) e un muro tra (R3,C3)(R4,C4). ogni muro occupa due celle
% newPos(R,C). indica che vogliamo muoverci nella cella (R,C)
% restituisco o un nuovo muro o una nuova posizione, non entrambi

%----------------ESEMPIO DI INPUT----------------
%numPlayers(2). maxWall(6).
%me(1). player(0,2,8,4,"S"). player(1,5,2,6,"N").
%wall(4,0,5,0, 4,1,5,1). wall(4,2,5,2, 4,3,5,3).

%numPlayers(2). maxWall(6).
%me(1). player(0,0,7,0,"E"). player(1,1,3,1,"N").
%wall(0,1,1,1, 0,2,1,2). wall(0,5,1,5, 0,6,1,6). wall(0,7,1,7, 0,8,1,8).
%wall(1,7,1,8, 2,7,2,8). wall(3,7,3,8, 4,7,4,8). wall(5,7,5,8, 6,7,6,8). wall(7,7,7,8, 8,7,8,8).

%--------MODELLAZIONE AMBIENTE GIOCO------------
row(0..8).
col(0..8).  % anche questi due dobbiamo prenderli in input
cell(R,C) :- row(R), col(C).

singleWall(R1,C1,R2,C2) :- wall(R1,C1,R2,C2,  _,_, _,_).   %viene utile dividere il muro unico di due celle in due singleWall per i vari controlli
singleWall(R1,C1,R2,C2) :- wall(_,_, _,_,  R1,C1,R2,C2).
singleWall(R2,C2,R1,C1) :- singleWall(R1,C1,R2,C2).

winningPos("N", 0,C) :- col(C). % se la direzione di vittoria è nord, la posizione di vittoria è in (0,C) per ogni C. --> (0,1), (0,2), ..., (0,8)
winningPos("S", 8,C) :- col(C). % se la direzione di vittoria è sud, la posizione di vittoria è in (8,C) per ogni C. --> (8,1), (8,2), ..., (8,8)
winningPos("E", R,8) :- row(R). % se la direzione di vittoria è est, la posizione di vittoria è in (R,8) per ogni R. --> (1,8), (2,8), ..., (8,8)
winningPos("W", R,0) :- row(R). % se la direzione di vittoria è ovest, la posizione di vittoria è in (R,0) per ogni R. --> (1,0), (2,0), ..., (8,0)

% genero tutte le celle adiacenti, in quanto non posso andare in tutte le direzioni se c'è un muro. Utile per il controllo di validità del cammino
adjacent(R1,C1, R2,C1) :- cell(R1,C1), cell(R2,C1), not singleWall(R1,C1,R2,C1), Diff=R1-R2, &abs(Diff;Z), Z=1.
adjacent(R1,C1, R1,C2) :- cell(R1,C1), cell(R1,C2), not singleWall(R1,C1,R1,C2), Diff=C1-C2, &abs(Diff;Z), Z=1.

%--------------------------CAMMINO MINIMO INIZIALE----------------------------
%per calcolare la distanza minima di ogni giocatore per vincere attuale. Prima di generare nuovi muri, o di spostarmi
%per capire se sto peggiorando la situazione.
pathInitial(R1,C1, R2,C2, 1) :- adjacent(R1,C1, R2,C2), player(_,R1,C1,_,_).
pathInitial(R1,C1, R3,C3, K) :- pathInitial(R1,C1, R2,C2,K1), adjacent(R2,C2, R3,C3),K=K1+1,K<=CL,costLimitPaths(CL).
minDistanceInitial(ID,Cost) :- player(ID,R,C,_,Dir), pathInitial(R,C,_,_,Cost), Cost = #min{K,Rwin,Cwin  : pathInitial(R,C,Rwin,Cwin,K), winningPos(Dir, Rwin, Cwin)}.

%--------------------------GUESS & CHECK --------------------------------
%guess di tutte le posizioni che posso raggiungere, già nel guess decido solo tra quelle adiacenti a me per ottimizare.
newPos(NewR,NewC) | noNewPos(NewR,NewC) :- cell(NewR,NewC), player(ID, R,C, _,_), me(ID), adjacent(R,C, NewR,NewC).

%guess muri orizzontali, controllando che non ci sono muri già presenti nello stesso verso (completamente o parzialmente) per ottimizzare.
newWall(R1,C1,R2,C2, R3,C3,R4,C4) | noNewWall(R1,C1,R2,C2, R3,C3,R4,C4) :- cell(R1,C1), cell(R2,C2), cell(R3,C3), cell(R4,C4), R1=R3, R2=R4, R1=R2-1, R3=R4-1, C1=C2, C3=C4, C1=C3-1, C2=C4-1.
%guess per i muri verticali uguale a quello orizzontale
newWall(R1,C1,R2,C2, R3,C3,R4,C4) | noNewWall(R1,C1,R2,C2, R3,C3,R4,C4) :- cell(R1,C1), cell(R2,C2), cell(R3,C3), cell(R4,C4), R1=R2, R3=R4, R1=R3-1, R2=R4-1, C1=C3, C2=C4, C1=C2-1, C3=C4-1.

%--------------------------CONTROLLO DELLE REGOLE----------------------------
%Controllo che il muro che sto piazzando non si sovrapponga parzialmente o completamente con uno esistente.
%verticale
:- newWall(R1,C1,R1,C1+1,  R1+1,C1,R1+1,C1+1), singleWall(R1,C1, R1,C1+1).
:- newWall(R1,C1,R1,C1+1,  R1+1,C1,R1+1,C1+1), singleWall(R1+1,C1, R1+1,C1+1).
%orizzontale
:- newWall(R1,C1,R1+1,C1,  R1,C1+1,R1+1,C1+1), singleWall(R1,C1, R1+1,C1).
:- newWall(R1,C1,R1+1,C1,  R1,C1+1,R1+1,C1+1), singleWall(R1,C1+1, R1+1,C1+1).

%Controllo che il muro che sto piazzando non sia a croce con uno già esistente. per essere a croce tutti e 4 i vertici devono essere adiacenti
:- newWall(R1,C1,R2,C2,  R3,C3,R4,C4), wall(R1,C1,R3,C3, R2,C2,R4,C4).

%conto quanti nuovi muri o posizioni ho generato, devo essere sicuro che almeno uno dei due sia stato generato.
newPosNum(N) :- N = #count{NewR,NewC : newPos(NewR,NewC)}.
newWallNum(N) :- N = #count{R1,C1,R2,C2, R3,C3,R4,C4 : newWall(R1,C1,R2,C2,  R3,C3,R4,C4)}.

% o restituisco un nuovo muro o una nuova posizione, non entrambi, e nemmeno nessuna.
:- newPosNum(N), newWallNum(M), N+M!=1.

%non è possibile piazzare un nuovo muro se ho finito i muri
:- newWallNum(N), player(ID, _,_, 0,_), me(ID), N > 0.

newSingleWall(R1,C1, R2,C2) :- newWall(R1,C1,R2,C2, _,_,_,_).
newSingleWall(R3,C3,R4,C4) :- newWall(_,_,_,_, R3,C3,R4,C4).
newSingleWall(R2,C2,R1,C1) :- newSingleWall(R1,C1, R2,C2).

%non è possibile che il nuovo muro messo, blocca completamente il cammino di un giocatore verso la vittoria
playerCanWin(ID) :- player(ID,R,C,_,_), minPathToWinAfter(ID,R,C,_).  % se esiste un cammino minimo verso la vittoria vuol dire che non è bloccato
:- newWallNum(N), N > 0, player(ID,_,_,_,_), not playerCanWin(ID).

%--------------------------RICALCOLO CAMMINO MINIMO DOPO CHE HO PIAZZATO UN NEW WALL--------------------------
%trovo tutte le celle che ogni giocatore può raggiungere dalla sua posizione attuale
%derivo tutti questi cammini solo se nel guess ho effettivamente generato un newWall, altrimenti se non decido di piazzare un nuovo
%nel mio turno, tutti i cammini minimi rimangono quelli iniziali. Ottimizzazione UltraIstinct.
pathAfter(R1,C1, R2,C2, 1) :- adjacent(R1,C1, R2,C2), player(_,R1,C1,_,_), not newSingleWall(R1,C1, R2,C2), newWallNum(1).
pathAfter(R1,C1, R3,C3, K) :- pathAfter(R1,C1, R2,C2,K1), adjacent(R2,C2, R3,C3),K=K1+1,K<=CL, not newSingleWall(R2,C2, R3,C3),costLimitPaths(CL).

%numero minimo passi giocatore dall'obiettivo.
minPathToWinAfter(ID, R,C, Cost) :- pathAfter(R,C, _,_, Cost),player(ID,R,C,_,D), Cost=#min{K,Rwin,Cwin : pathAfter(R,C,Rwin,Cwin, K), winningPos(D, Rwin, Cwin)}.

%calcolo l'ID dei giocatori più vicini alla vittoria e quanti passi mancano a lui/loro per vincere
%nearWinAfter(ID, Cost) :- minPathToWinAfter(ID,_,_,Cost), Cost = #min{K,ID1 : minPathToWinAfter(ID1,_,_,K)}.
nearWinInitial(ID, DistToWin) :- minDistanceInitial(ID, DistToWin), DistToWin=#min{K,Id1 : minDistanceInitial(Id1,K)}.

%calcolo la differenza tra me e il giocatore più vicino alla vittoria, nel caso in cui non sono io.
discardToAvvInitial(IDAvv,D) :- nearWinInitial(IDAvv,N), me(ID), minDistanceInitial(ID,K), ID!=IDAvv, N<K, D=K-N.

% NUOVO CAMMINO MINIMO PER LE NUOVE POSIZIONI, per sapere se mi sto allontanando o avvicinando all'obiettivo.
% anche questo viene derivato solo se mi sono mosso.
newPath(R1,C1, R2,C2, 1) :- adjacent(R1,C1, R2,C2), newPos(R1,C1).
newPath(R1,C1, R3,C3, K) :- newPath(R1,C1, R2,C2,K1), adjacent(R2,C2, R3,C3),K=K1+1,K<=CL,costLimitPaths(CL).
%numero minimo passi giocatore dall'obiettivo.
newMinPathToWin(R,C, Cost) :- newPos(R,C), me(ID), player(ID,_,_,_,D), Cost=#min{K,Rwin,Cwin : newPath(R,C,Rwin,Cwin, K), winningPos(D, Rwin, Cwin)}.

% se posso vincere vinco, derivo direttamente per ottimizzare, indipendentemente da strategie e roba varia. Unica condizione strong
:-player(ID,1,C,_,"N"), me(ID), adjacent(1,C,0,C), not newPos(0,C).
:-player(ID,7,C,_,"S"), me(ID), adjacent(7,C,8,C), not newPos(8,C).
:-player(ID,R,7,_,"E"), me(ID), adjacent(R,7,R,8), not newPos(R,8).
:-player(ID,R,1,_,"W"), me(ID), adjacent(R,1,R,0), not newPos(R,0).

%---------------------STRATEGIE-----------------------------

%-----GENERALE-----
% se mi muovo, devo muovermi verso l'obiettivo, in modo da ridurre il cammino minimo verso l'obiettivo
% ogni volta che mi muovo, pago tanto quanto sono i passi dalla mia newPos alla mia winningPos.
:~ newPos(R1,C1), minDistanceInitial(ID,K), me(ID), newMinPathToWin(R1,C1,K1). [K1@3]

%non vorrei avvicinarmi ad un muro. Ad esempio se a 2 passi a destra di me c'è un muro verticale e devo andare Est, invece di fare
% passo a destra-->passo sopra/sotto per evitare il muro, faccio passo sopra/sotto --> passo a destra. In modo da tenermi lontano di una
% cella dal muro, e avere più possibilità di movimento e ridurre il rischio di essere chiuso, senza però inficiare il cammino minimo verso l'obiettivo.
:~ newPos(R,C), player(ID,_,_,_,"N"), me(ID), singleWall(R-1,C,R,C). [1@2]
:~ newPos(R,C), player(ID,_,_,_,"S"), me(ID), singleWall(R,C,R+1,C). [1@2]
:~ newPos(R,C), player(ID,_,_,_,"W"), me(ID), singleWall(R,C-1,R,C). [1@2]
:~ newPos(R,C), player(ID,_,_,_,"E"), me(ID), singleWall(R,C,R,C+1). [1@2]

%Non voglio stare ai lati della griglia, così da ridurre la possibilità che mi chiudono e dover fare più passi per raggiungere l'obiettivo
:~ newPos(R,C), player(ID,_,_,_,"N"), me(ID), P=4-C, &abs(P;Z), Z=4. [1@1]
:~ newPos(R,C), player(ID,_,_,_,"S"), me(ID), P=4-C, &abs(P;Z), Z=4. [1@1]
:~ newPos(R,C), player(ID,_,_,_,"W"), me(ID), P=4-R, &abs(P;Z), Z=4. [1@1]
:~ newPos(R,C), player(ID,_,_,_,"E"), me(ID), P=4-R, &abs(P;Z), Z=4. [1@1]


%-----EARLY GAME-----
% Strategia tiro dritto finchè qualcuno non piazza un muro. Molto aggressivo in early.
% Altrimenti se nessuno piazza muri, si rischia di perdere. Gioco aggressivo quindi per circa i primi 3/4 turni iniziali di ogni
% giocatore, pensando solo ad andare avanti per provare ad acquisire un vantaggio. Se nessuno piazza muri, e il più vicino è a 4 passi
% dalla vittoria, passo alla strategia "mid game". Se questa strategia termina non perchè qualcuno ha iniziato a piazzare muri, ma
% perchè il più vicino è a 7 passi dalla vittoria, e quel vicino sono io, la strategia "mid game" comunque mi dirà di tirare dritto.
% in caso contrario inizio a piazzare muri.
%:~ #count{ID : player(ID,_,_,NW,_)}=NP, newWallNum(1), maxWall(NW), numPlayers(NP), nearWinInitial(_,Kwin), Kwin>4. [1@10]

%NUOVA VERSIONE: indipendentemente se gli avversari piazzano muri o meno, inizio a piazzare muri solo quando c'è qualcuno a 3
%passi dalla vittoria, in modo da essere molto aggressivo nella fase iniziale.
:~ newWallNum(1), nearWinInitial(_,Kwin), Kwin>3. [1@7]

%-----MID GAME / END GAME-----

% --- MURO CHE DANNEGGIA PIù GIOCATORI POSSIBILI IN VANTAGGIO
% vincolo per assicurarmi che con il muro messo, il cammino minimo del giocatore più vicino alla vittoria è peggiore di prima.
% INOLTRE, per come è fatto il vincolo, trovo il muro che danneggia più giocatori possibili: ad esempio se ci sono due giocatori più vicini alla vittoria, con lo
% stesso numero di passi, me li derivo entrambi, quindi avrò ad esempio:
% nearWinInitial(1,3). nearWinInitial(2,3). minPathToWinAfter(1,_,_,5). minPathToWinAfter(2,_,_,3).
%oppure:
% nearWinInitial(1,3). nearWinInitial(2,3). minPathToWinAfter(1,_,_,5). minPathToWinAfter(2,_,_,4).
% in questo caso il vincolo sceglie il secondo, che è quello da preferire, in quanto danneggia entrambi i giocatori.
%potrei anche non controllare se ho messo un nuovo muro, in quanto con l'ottimizzazione minPathToWinAfter lo genera solo se ho messo un muro col guess (??)
:~ newWallNum(1), nearWinInitial(IDAvv,D), me(ID), IDAvv!=ID, minPathToWinAfter(IDAvv,_,_,K),  D1=50-K. [D1@4,IDAvv,D,K]
% se ci sono due muri che mi danneggiano allo stesso modo il/i giocatore/i più vicino/i, scelgo quello che mi danneggia meno.
:~ newWallNum(1), nearWinInitial(ID,D), me(ID), minPathToWinAfter(ID,_,_,K),  D1=D+K. [D1@3]

%devo inoltre pagare se il discardToAvvInitial non è presente e metto un muro. potrei farla anche con il nearWinInitial.
:~ nearWinInitial(ID,_), me(ID), newWallNum(1). [1@5]

%se discardToAvvInitial è presente e io mi muovo vuol dire che è in vantaggio l'avversario e non sto piazzando un muro, quindi pago.
:~ discardToAvvInitial(_,_), newPos(_,_). [1@6]

%VERSIONI ALTERNATIVE
% blocco l'avversario solo se è in vantaggio e se è vicino alla vittoria, più abbasso il confronto e più è pericoloso e aggressivo
% INUTILE se decido di giocare già aggressivo con il vincolo della strategia EARLY
% :~ discardToAvvInitial(_,_), newPos(_,_), nearWinInitial(_,Kwin), Kwin<3. [1@6]
% qui ragiono sulla distanza tra me e l'avversario in vantaggio, se il suo vantaggio non è superiore di due passi rispetto a me
% lo ignoro e lo faccio bloccare dagli altri avversari
%:~ discardToAvvInitial(IDAvv, D), nearWinInitial(IDAvv,Kwin), D > 2, Kwin < 2, newPos(_,_). [1@6]

%se nessun muro può ostacolare i giocatori più vicini alla vittoria, allora non serve che piazzo muri inutili, (possibili cause di loop).
% questo è l'unico caso in cui anche non essendo il più vicino alla vittoria, continuo a camminare dritto senza piazzare muri inutili.
numeroGiocatoriProssimiAllaVittoria(N) :- N = #count{ID : nearWinInitial(ID,_)}.
nessunMuroPuoOstacolareIPiuVicini :- #count{IDAvv :  nearWinInitial(IDAvv,D), me(ID), IDAvv!=ID, minPathToWinAfter(IDAvv,_,_,D)}=N, numeroGiocatoriProssimiAllaVittoria(N).
:~ newWallNum(1), nessunMuroPuoOstacolareIPiuVicini. [2@6]

%#show maxWall/1.
%#show player/5.
#show newWall/8.
#show newPos/2.
#show minPathToWinAfter/4.
#show nearWinInitial/2.
#show discardToAvvInitial/2.
#show minDistanceInitial/2.
%#show pathAfter/5.
#show newMinPathToWin/3.
%#show newWallNum/1.
%#show singleWall/4.
%#show wall/8.
#show costLimitPaths/1.


