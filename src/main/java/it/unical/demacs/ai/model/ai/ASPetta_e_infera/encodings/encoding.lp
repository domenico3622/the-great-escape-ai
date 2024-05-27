% fatti
row(1).
row(R) :- R = R1 + 1, row(R1), R <= D, dimension(D).
column(1).
column(C) :- C = C1 + 1, row(C1), C <= D, dimension(D).
node(pos(R, C)) :- row(R), column(C).
edge(pos(R, C1), pos(R, C2)) :- C1-C2 = 1, node(pos(R, C1)), node(pos(R, C2)).
edge(pos(R1, C), pos(R2, C)) :- R1-R2 = 1, node(pos(R1, C)), node(pos(R2, C)).
edge(A, B) :- edge(B, A). % per la simmetria.

% input: 
% - wallRaw(Id, R1, C1, R2, C2)      % posizione dei muri in input
% - playerRaw(Id, R, C, D)                % posizione giocatori
% - me(Id)                                  % chi sono io?

wall(Id, pos(R1, C1), pos(R2, C2)) :- wallRaw(Id, R1, C1, R2, C2).
player(Id, pos(R, C), right) :- playerRaw(Id, R, C, "RIGHT", _). 
player(Id, pos(R, C), left) :- playerRaw(Id, R, C, "LEFT", _). 
player(Id, pos(R, C), up) :- playerRaw(Id, R, C, "UP", _).
player(Id, pos(R, C), down) :- playerRaw(Id, R, C, "DOWN", _).
numWallsAvailable(P, N) :- playerRaw(P, _, _, _, N). 

wallId(Id) :- wall(Id, _, _).
costLimit(N) :- #count{P1, P2 : wallWithoutId(P1, P2)} = N1, dimension(D), N = D + N1.


% guess sulla mossa da fare
move(down) | move(up) | move(right) | move(left) | noMove(h) | noMove(v).
yesMove :- move(X).
noMove :- noMove(X).
:- noMove, numWallsAvailable(P, N), N=0, me(P).
{ inWallLong(pos(R, C), pos(R+1, C), pos(R, C+1), pos(R+1, C+1)) :
        edge(pos(R, C), pos(R+1, C)), edge(pos(R, C+1), pos(R+1, C+1)),
        not wallWithoutId(pos(R, C),pos(R+1, C)),
        not wallWithoutId(pos(R+1, C),pos(R, C)),
        not wallWithoutId(pos(R, C+1), pos(R+1, C+1)),
        not wallWithoutId(pos(R+1, C+1), pos(R, C+1))} = 1 :- noMove(h).
{ inWallLong(pos(R, C), pos(R, C+1), pos(R+1, C), pos(R+1, C+1)) : 
        edge(pos(R, C), pos(R, C+1)), edge(pos(R+1, C), pos(R+1, C+1)), 
        not wallWithoutId(pos(R, C),pos(R, C+1)), 
        not wallWithoutId(pos(R, C+1),pos(R, C)), 
        not wallWithoutId(pos(R+1, C), pos(R+1, C+1)), 
        not wallWithoutId(pos(R+1, C+1), pos(R+1, C))} = 1 :- noMove(v).

inWall(Pos1, Pos2) :- inWallLong(Pos1, Pos2, _, _).
inWall(Pos1, Pos2) :- inWallLong(_, _, Pos1, Pos2).

% check sui muri inseriti
%okWall1 :- inWall(Pos1, Pos2), inWall(Pos3, Pos4), edge(Pos1, Pos3), edge(Pos2, Pos4), Pos1 != Pos3, Pos2 != Pos4, Pos1 != Pos4, Pos2 != Pos3. % controllo che i muri siano vicini, sia se sono orizzontali, sia se sono verticali
%:- not okWall1, noMove. % check
:- inWall(Pos1, Pos2), inWall(Pos3, Pos4), wall(Id, Pos1, Pos3), wall(Id, Pos2, Pos4), wallId(Id). % controllo che due muri non si intersecano

%check sulle mose
:- move(down), me(P), player(P, pos(R, C), D), dimension(R).
:- move(up), me(P), player(P, pos(R, C), D), R = 1.
:- move(right), me(P), player(P, pos(R, C), D), dimension(C).
:- move(left), me(P), player(P, pos(R, C), D), C = 1.
:- move(down), me(P), player(P, pos(R, C), D), wallWithoutId(pos(R, C), pos(R+1, C)).
:- move(up), me(P), player(P, pos(R, C), D), wallWithoutId(pos(R, C), pos(R-1, C)).
:- move(right), me(P), player(P, pos(R, C), D), wallWithoutId(pos(R, C), pos(R, C+1)).
:- move(left), me(P), player(P, pos(R, C), D), wallWithoutId(pos(R, C), pos(R, C-1)).

% calcolo le posizioni obiettivo
obj(D, pos(R, Dim)) :- node(pos(R, Dim)), D = right, dimension(Dim).
obj(D, pos(R, 1)) :- node(pos(R, 1)), D = left.
obj(D, pos(1, C)) :- node(pos(1, C)), D = up.
obj(D, pos(Dim, C)) :- node(pos(Dim, C)), D = down, dimension(Dim).

%wall without id
wallWithoutId(Pos1, Pos2) :- wall(Id, Pos1, Pos2).
wallWithoutId(Pos2, Pos1) :- wall(Id, Pos1, Pos2).

% calcolo i cammini prima
inPathBefore(P, Pos1, Pos2, 1) :- player(P, Pos1, _), edge(Pos1, Pos2), not wallWithoutId(Pos1, Pos2).
inPathBefore(P, Pos1, Pos2, N) :- player(P, _, D), inPathBefore(P, _, Pos1, N1), edge(Pos1, Pos2), not wallWithoutId(Pos1, Pos2), N = N1 + 1, not obj(D, Pos1), N < Cost, costLimit(Cost).
lenPathBefore(P, N) :- player(P, _, D), #min{N1 : inPathBefore(P, _, Pos, N1), obj(D, Pos)} = N.
arriveIn(P) :- inPathBefore(P, _, Pos, _), player(P, _, D), obj(D, Pos).
:- player(P, _, _), not arriveIn(P).

% creo i nuovi muri TODO: modificare
newWall(Pos1, Pos2) :- wallWithoutId(Pos1, Pos2).
newWall(Pos1, Pos2) :- inWall(Pos1, Pos2).
newWall(Pos1, Pos2) :- inWall(Pos2, Pos1).

newPos(pos(R+1, C)) :- player(P, pos(R, C), _), me(P), move(down).
newPos(pos(R-1, C)) :- player(P, pos(R, C), _), me(P), move(up).
newPos(pos(R, C+1)) :- player(P, pos(R, C), _), me(P), move(right).
newPos(pos(R, C-1)) :- player(P, pos(R, C), _), me(P), move(left).
newPos(Pos) :- noMove, player(P, Pos, _), me(P).

% calcolo i cammini dopo
inPathAfter(P, Pos1, Pos2, 1) :- newPos(Pos1), edge(Pos1, Pos2), not newWall(Pos1, Pos2), me(P).
inPathAfter(P, Pos1, Pos2, 1) :- player(P, Pos1, _), edge(Pos1, Pos2), not newWall(Pos1, Pos2), not me(P), noMove.
inPathAfter(P, Pos1, Pos2, N) :- player(P, _, D), inPathAfter(P, _, Pos1, N1), edge(Pos1, Pos2), not newWall(Pos1, Pos2), N = N1 + 1, not obj(D, Pos1), N < Cost, costLimit(Cost).
lenPathAfter(P, N) :- player(P, _, D), #min{N1 : inPathAfter(P, _, Pos, N1), obj(D, Pos)} = N, me(P).
lenPathAfter(P, N) :- player(P, _, D), #min{N1 : inPathAfter(P, _, Pos, N1), obj(D, Pos)} = N, not me(P), noMove.
arriveInAfter(P) :- inPathAfter(P, _, Pos, _), player(P, _, D), obj(D, Pos).
:- me(P), not arriveInAfter(P).
:- not me(P), not arriveInAfter(P), noMove, player(P, _, _).

dif(P, N) :- lenPathBefore(P, N1), lenPathAfter(P, N2), N = N2 - N1.

iAmFirst :- lenPathBefore(P, N), me(P), #min{N1 : lenPathBefore(P1, N1)} = N.

firstEnemy(P) :- not iAmFirst, lenPathBefore(P, N), #min{N1 : lenPathBefore(P1, N1)} = N.

playerNearWin(P) :- player(P, _, _), lenPathBefore(P, N), N <= 3.

%%%  weak constraints: %%%

% Se sono primo, corro verso la vittoria
:- iAmFirst, noMove.

:~ #count{P : playerNearWin(P), not me(P)} = 0, noMove. [1@5]

:~ playerNearWin(P), yesMove. [1@4, P]

:~ dif(P, N), firstEnemy(P), N1 = 100 - N, Res = N1 * 5. [Res@3, P, Res]
:~ dif(P, N), N1 = 100 - N, Res = N1 * 3, not me(P). [Res@3, P, Res]
:~ dif(P, N), me(P), noMove, N1 = N*20. [N1@3]

:~ me(P), lenPathAfter(P, N), yesMove. [N@1]

% output muri da piazzare
placeWall(R1, C1, R2, C2, R3, C3, R4, C4, vertical) :- inWall(pos(R1, C1), pos(R2, C2)), inWall(pos(R3, C3), pos(R4, C4)), R1 < R3.
placeWall(R1, C1, R2, C2, R3, C3, R4, C4, horizontal) :- inWall(pos(R1, C1), pos(R2, C2)), inWall(pos(R3, C3), pos(R4, C4)), C1 < C3.

#show placeWall/9.
#show move/1.
#show dif/2.
#show lenPathBefore/2.
#show lenPathAfter/2.
#show playerNearWin/1.
#show me/1.
#show costLimit/1.