#include <iostream>
#include <vector>
#include <random>

// Moduł przechowujący stan gry
namespace game_state {
    const int BOARD_SIZE = 3;
    enum class CellState { Empty, Cross, Circle };
    std::vector<CellState> board(BOARD_SIZE * BOARD_SIZE, CellState::Empty);

    // Funkcja wyświetlająca planszę
    void print_board() {
        std::cout << "  ";
        for (int col = 0; col < BOARD_SIZE; col++) {
            std::cout << col << " ";
        }
        std::cout << std::endl;

        for (int row = 0; row < BOARD_SIZE; row++) {
            std::cout << row << " ";
            for (int col = 0; col < BOARD_SIZE; col++) {
                std::cout << "+-";
            }
            std::cout << "+" << std::endl;

            std::cout << row << " ";
            for (int col = 0; col < BOARD_SIZE; col++) {
                std::cout << "|";
                int index = row * BOARD_SIZE + col;
                switch (board[index]) {
                    case CellState::Empty:
                        std::cout << " ";
                        break;
                    case CellState::Cross:
                        std::cout << "X";
                        break;
                    case CellState::Circle:
                        std::cout << "O";
                        break;
                }
            }
            std::cout << "|" << std::endl;
        }

        std::cout << BOARD_SIZE << " ";
        for (int col = 0; col < BOARD_SIZE; col++) {
            std::cout << "+-";
        }
        std::cout << "+" << std::endl;
    }
}

// Moduł ze sztuczną inteligencją
namespace ai {
    // Funkcja losująca wolne pole
    int choose_random_move() {
        std::vector<int> free_cells;
        for (int i = 0; i < game_state::BOARD_SIZE * game_state::BOARD_SIZE; i++) {
            if (game_state::board[i] == game_state::CellState::Empty) {
                free_cells.push_back(i);
            }
        }
        if (free_cells.empty()) {
            return -1;
        }

        int randomIndex = rand() % free_cells.size();
        return free_cells[randomIndex];
    }
}

// Moduł do komunikacji z użytkownikiem i prezentacji stanu gry
namespace user_interface {
    // Funkcja obsługująca ruch użytkownika
    bool handle_user_move() {
        int row, col;
        std::cout << "Your turn (row column): ";
        std::cin >> row >> col;
        int index = row * game_state::BOARD_SIZE + col;
        if (row < 0 || row >= game_state::BOARD_SIZE ||
            col < 0 || col >= game_state::BOARD_SIZE ||
            game_state::board[index] != game_state::CellState::Empty) {
            std::cout << "Invalid move!" << std::endl;
            return false;
        }
        game_state::board[index] = game_state::CellState::Circle;
        return true;
    }

    // Funkcja obsługująca ruch komputera
    void handle_computer_move() {
        int index = ai::choose_random_move();

        int row = index / game_state::BOARD_SIZE;
        int col = index % game_state::BOARD_SIZE;
        game_state::board[index] = game_state::CellState::Cross;

        std::cout << "Computer's turn (row column): " << row << " " << col << std::endl;
    }

    // Funkcja inicjująca i wywołująca grę
    void play_game() {
        while (true) {
            game_state::print_board();

            if (handle_user_move()) {
                handle_computer_move();
            } else {
                continue;
            }

            // Sprawdzenie warunku zwycięstwa użytkownika
            bool user_won = false;
            for (int row = 0; row < game_state::BOARD_SIZE; row++) {
                bool row_won = true;
                for (int col = 0; col < game_state::BOARD_SIZE; col++) {
                    int index = row * game_state::BOARD_SIZE + col;
                    if (game_state::board[index] != game_state::CellState::Circle) {
                        row_won = false;
                        break;
                    }
                }
                if (row_won) {
                    user_won = true;
                    break;
                }
            }

            if (user_won) {
                std::cout << "You won!" << std::endl;
                break;
            }

            // Sprawdzenie warunku remisu
            bool draw = true;
            for (int i = 0; i < game_state::BOARD_SIZE * game_state::BOARD_SIZE; i++) {
                if (game_state::board[i] == game_state::CellState::Empty) {
                    draw = false;
                    break;
                }
            }
            if (draw) {
                std::cout << "Draw!" << std::endl;
                break;
            }

            // Sprawdzenie warunku zwycięstwa komputera
            bool computer_won = false;
            for (int row = 0; row < game_state::BOARD_SIZE; row++) {
                bool row_won = true;
                for (int col = 0; col < game_state::BOARD_SIZE; col++) {
                    int index = row * game_state::BOARD_SIZE + col;
                    if (game_state::board[index] != game_state::CellState::Cross) {
                        row_won = false;
                        break;
                    }
                }
                if (row_won) {
                    computer_won = true;
                    break;
                }
            }

            if (computer_won) {
                std::cout << "Computer won!" << std::endl;
                break;
            }
        }

    game_state::print_board();
    }
} // namespace user_interface

int main()
{
    std::cout << "Tic Tac Toe game!" << std::endl;
    user_interface::play_game();
    
    return 0;
}
