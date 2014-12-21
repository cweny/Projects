//Shuffles the columns to evaluate depending on the input
var sCols = function(play) {
    var shuffled = [];
    var t = 1;
    var start = parseInt(play);
    shuffled.push(start);
    while (t + start < columnSize || start - t >= 0) {
        if (start - t >= 0) {
            shuffled.push(start - t);
        }
        if (start + t < columnSize) {
            shuffled.push(start + t);
        }
        t++;
    }
    return shuffled;
};
//returns the opponent of the inputed player
var getOpponent = function(player) {
	if(player == 1) {
		return 2;
	} else if(player ==2) {
		return 1;
	}
	console.log("error");
	return 0;
}
//returns an empty board with no pieces
var newBoard = function() {
    var newB = [];

    for (var i = 0; i < rowSize; i++) {
        var temp = [];
        for (var n = 0; n < columnSize; n++) {
            temp[n] = 0;
        }
        newB[i] = temp;
    }
    return newB;
};
//Returns the row where a piece can be dropped. -1 if no piece can be dropped at the specified column
var droppableColumn = function(col) {
    var row = rowSize - 1;
    while (board[row][col] !== 0) {
        row--;
        if (row === -1) {
            return row;
        }
    }
    return row;
};
//Deletes the last play made in the column specified
var undoColumn = function(col) {
    var row = 0;
    while (board[row][col] === 0) {
        row++;
        if (row === rowSize) {
            console.log("error");
            return;
        }
    }

    board[row][col] = 0;
};
//set background of element to red
var setRed = function() {
    this.style.backgroundColor = "red";
};
//set background of element to blue
var setBlue = function() {
    this.style.backgroundColor = "blue";
};
//set background of element to white
var setWhite = function() {
    this.style.backgroundColor = "white";
};
//return the winner. 0 if there is no winner
var checkWin = function() {
    for (var row = 0; row < board.length; row++) {
        for (var col = 0; col < board[0].length; col++) {
            var player = board[row][col];
            if (player === 0) {
                continue;
            }
            if (row + 3 < board.length && board[row + 1][col] === player && board[row + 2][col] === player && board[row + 3][col] === player)
                return player;
            if (col + 3 < board[0].length && board[row][col + 1] === player && board[row][col + 2] === player && board[row][col + 3] === player)
                return player;
            if ((row + 3 < board.length && col + 3 < board[0].length) && board[row + 1][col + 1] === player && board[row + 2][col + 2] === player && board[row + 3][col + 3] === player)
                return player;
            if ((row - 3 > 0 && col + 3 < board[0].length) && board[row - 1][col + 1] === player && board[row - 2][col + 2] === player && board[row - 3][col + 3] === player)
                return player;
        }
    }
    return 0;
};
//Drop a piece in the specified element
var drop = function(element, player) {
    var row = rowSize - 1;
    while (board[row][element.id] !== 0) {
        row--;
        if (row === -1) {
            break;
        }
    }
    if (row !== -1) {
        if (redTurn) {
            board[row][element.id] = 1;
            $('#player').text("Player Blue Turn");
        } else {
            board[row][element.id] = 2;
            $('#player').text("Player Red Turn");
        }
        var slots = $(".row")[row].childNodes;
        var x = slots[element.id];
        if (redTurn) {
            x.style.backgroundColor = "red";
            x.onmouseout = setRed;
        } else {
            x.style.backgroundColor = "blue";
            x.onmouseout = setBlue;
        }

        redTurn = !redTurn;
    }
    if (row > 0 && player === 1) {
        var slots = $(".row")[row-1].childNodes;
        var x = slots[element.id];
        x.style.backgroundColor = "pink";
    }
    if (row !== -1) {
        return true;
    } else {
        return false;
    }
};
var compare = function(a2, a1) {
	for (var i in a1) {
		a2.splice(a2.indexOf(a1[i]),1);
	}
	return a2.length;
}
//Delete array of elements from specified player combinations and add array of elements
var deleteCombinations = function(addTo1, addTo2, deleteFromPlayer, player) {
	boardComb1.three = addTo1.three.concat(boardComb1.three);
	boardComb1.two = addTo1.two.concat(boardComb1.two);
	boardComb1.one = addTo1.one.concat(boardComb1.one);
	
	boardComb2.three = addTo2.three.concat(boardComb2.three);
	boardComb2.two = addTo2.two.concat(boardComb2.two);
	boardComb2.one = addTo2.one.concat(boardComb2.one);
	
	if(player === 1) {
		for(var i in deleteFromPlayer.three) {
			boardComb1.three.splice(boardComb1.three.indexOf(deleteFromPlayer.three[i]), 1);
		}
		for(var i in deleteFromPlayer.two) {
			boardComb1.two.splice(boardComb1.two.indexOf(deleteFromPlayer.two[i]), 1);
		}
		for(var i in deleteFromPlayer.one) {
			boardComb1.one.splice(boardComb1.one.indexOf(deleteFromPlayer.one[i]), 1);
		}
	} else if(player === 2) {
		for(var i in deleteFromPlayer.three) {
			boardComb2.three.splice(boardComb2.three.indexOf(deleteFromPlayer.three[i]), 1);
		}
		for(var i in deleteFromPlayer.two) {
			boardComb2.two.splice(boardComb2.two.indexOf(deleteFromPlayer.two[i]), 1);
		}
		for(var i in deleteFromPlayer.one) {
			boardComb2.one.splice(boardComb2.one.indexOf(deleteFromPlayer.one[i]), 1);
		}
	} else {
		console.log("error");
	}
}
//Add specified array of elements and delete elements from board combinations
var addCombinations = function(deleteFrom1, deleteFrom2, addToPlayer, player) {
	for(var i in deleteFrom1.three) {
		boardComb1.three.splice(boardComb1.three.indexOf(deleteFrom1.three[i]), 1);
	}
	for(var i in deleteFrom1.two) {
		boardComb1.two.splice(boardComb1.two.indexOf(deleteFrom1.two[i]), 1);
	}
	for(var i in deleteFrom1.one) {
		boardComb1.one.splice(boardComb1.one.indexOf(deleteFrom1.one[i]), 1);
	}
	
	for(var i in deleteFrom2.three) {
		boardComb2.three.splice(boardComb2.three.indexOf(deleteFrom2.three[i]), 1);
	}
	for(var i in deleteFrom2.two) {
		boardComb2.two.splice(boardComb2.two.indexOf(deleteFrom2.two[i]), 1);
	}
	for(var i in deleteFrom2.one) {
		boardComb2.one.splice(boardComb2.one.indexOf(deleteFrom2.one[i]), 1);
	}
	if(player === 1) {
		boardComb1.three = addToPlayer.three.concat(boardComb1.three);
		boardComb1.two = addToPlayer.two.concat(boardComb1.two);
		boardComb1.one = addToPlayer.one.concat(boardComb1.one);
	} else if(player === 2) {
		boardComb2.three = addToPlayer.three.concat(boardComb2.three);
		boardComb2.two = addToPlayer.two.concat(boardComb2.two);
		boardComb2.one = addToPlayer.one.concat(boardComb2.one);
	} else {
		console.log("error");
	}
}
//Get current possible winning combinations with at least one piece on it
var getCombinations = function(player, col, row) {
	var combs = {
        four: [],
        three: [],
        two: [],
        one: []
    };
	// |
	for(var r = row; r <= row+3; r++) {
		if(r > 2 && r < rowSize) {
			var matches = 0;
			var under = 0;
			for(var r2 = r; r2 >= r-3; r2--) {
				if(board[r2][col] === player) {
					matches++;
				} else if(board[r2][col] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(r2===rowSize-1 || board[r2 + 1][col] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	// -
	for(var c = col; c <= col+3; c++) {
		if(c > 2 && c < columnSize) {
			var matches = 0;
			var under = 0;
			for(var c2 = c; c2 >= c-3; c2--) {
				if(board[row][c2] === player) {
					matches++;
				} else if(board[row][c2] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(row === rowSize-1 || board[row + 1][c2] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	// \	
	var c,r;
	for(c = col, r = row; c <= col+3; c++, r++) {
		if(c > 2 && c < columnSize && r > 2 && r < rowSize) {
			var matches = 0;
			var under = 0;
			var c2, r2;
			for(c2 = c, r2 = r; c2 >= c-3; c2--, r2--) {
				if(board[r2][c2] === player) {
					matches++;
				} else if(board[r2][c2] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(r2 === rowSize-1 || board[r2 + 1][c2] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	// /
	for(c = col, r = row; c <= col+3; c++, r--) {
		if(c > 2 && c < columnSize && r >= 0 && r < rowSize-3) {
			var matches = 0;
			var under = 0;
			var c2, r2;
			for(c2 = c, r2 = r; c2 >= c-3; c2--, r2++) {
				if(board[r2][c2] === player) {
					matches++;
				} else if(board[r2][c2] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(r2 === rowSize-1 || board[r2 + 1][c2] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	
	return combs;
}
//get combinations to change the "under" factor of evaluation
var getuCombinations = function(player, col, row) {
	var combs = {
        four: [],
        three: [],
        two: [],
        one: []
    };
	//-
	for(var c = col; c <= col+3; c++) {
		if(c > 2 && c < columnSize) {
			var matches = 0;
			var under = 0;
			for(var c2 = c; c2 >= c-3; c2--) {
				if(board[row][c2] === player) {
					matches++;
				} else if(board[row][c2] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(row === rowSize-1 || board[row + 1][c2] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	// \	
	var c,r;
	for(c = col, r = row; c <= col+3; c++, r++) {
		if(c > 2 && c < columnSize && r > 2 && r < rowSize) {
			var matches = 0;
			var under = 0;
			var c2, r2;
			for(c2 = c, r2 = r; c2 >= c-3; c2--, r2--) {
				if(board[r2][c2] === player) {
					matches++;
				} else if(board[r2][c2] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(r2 === rowSize-1 || board[r2 + 1][c2] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	// /
	for(c = col, r = row; c <= col+3; c++, r--) {
		if(c > 2 && c < columnSize && r >= 0 && r < rowSize-3) {
			var matches = 0;
			var under = 0;
			var c2, r2;
			for(c2 = c, r2 = r; c2 >= c-3; c2--, r2++) {
				if(board[r2][c2] === player) {
					matches++;
				} else if(board[r2][c2] === getOpponent(player)) {
					matches = 0;
					break;
				} else {
					if(r2 === rowSize-1 || board[r2 + 1][c2] !== 0) {
						under++;
					}
				}
			}
			switch(matches) {
			case 4:
                combs.four.push(1);
                break;
            case 3:
                combs.three.push(under);
                break;
            case 2:
                combs.two.push(under);
                break;
            case 1:
                combs.one.push(under);
                break;
			case 0:
				break;
			}
		}
	}
	
	return combs;
}
//Update "under" factor of combinations
var updateUnder = function(before1u, before2u, col,row) {
	var newc = {
		four: [],
		three: [],
		two: [],
		one: []
	};
		
	var after1u = getuCombinations(1, col, row-1);
	var after2u = getuCombinations(2, col, row-1);
	addCombinations(before1u,newc,after1u,1);
	addCombinations(newc,before2u,after2u,2);
}
//make a copy of the object
var copy = function(obj) {
	var copy1 = {};
		copy1.one = obj.one.slice(0);
		copy1.two = obj.two.slice(0);
		copy1.three = obj.three.slice(0);
	return copy1
}
