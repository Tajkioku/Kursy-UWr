# Kamil Woźniak 330601
# Lista 9 i 10

# Gra karciana: Wojna w Ruby
# Uruchomienie: ruby wojna.rb

# Zmiany do Listy 9 
# Dodany moduł zapisu/wczytu oraz interakcję użytkownika do zapisu stanu gry.
module Persistence
    # Metoda zapisu obiektu
    def save_to_file(filename)
        File.open(filename, 'wb') { |f| f.write(Marshal.dump(self)) }
    end

    # Metoda wczytania obiektu
    module ClassMethods
        def load_from_file(filename)
            File.open(filename, 'rb') { |f| Marshal.load(f.read) }
        end
    end

    # Po dołączeniu modułu rozszerzamy klasę o metody ClassMethods
    def self.included(base)
        base.extend ClassMethods
    end
end

# Zmiany do listy 10
# Wzorzec Template Method: dodano abstrakcyjną klasę TwoPlayerGame
class TwoPlayerGame
    include Persistence

    def initialize(players)
        @players = players
        setup
    end

    # Szablon głównej pętli
    def start
        round = 1
        until game_over?
            puts "-- Runda #{round} --"
            before_round
            play_round
            after_round
            round += 1
            sleep 0.5
        end
        announce_winner
    end

    def setup; end
    def before_round; end
    def play_round
        raise NotImplementedError
    end
    def after_round; end
    def game_over?
        raise NotImplementedError
    end
    def announce_winner
        raise NotImplementedError
    end
end

# Reszta kodu wspólna z dołączonymi klasami z listy 10
class Card
    include Comparable # Pozwala na porównanie wartości kart
    SUITS = { hearts: "♡", diamonds: "♢", clubs: "♧", spades: "♤" }
    VALUES = (2..10).to_a + %i[J Q K A]
    VALUE_RANK = VALUES.each_with_index.to_h
    @cards_cache = {}

    class << self
        def create(rank, suit)
            key = [rank, suit]
            @cards_cache[key] ||= new(rank, suit)
        end
        # Generuje pełną talię 52 kart
        def all
            VALUES.product(SUITS.keys).map { |r, s| create(r, s) }
        end
        def suits; SUITS.keys; end
    end

    attr_reader :rank, :suit
    def initialize(rank, suit)
        @rank, @suit = rank, suit # Przechowuje wartość i kolor karty
    end
    # Porównanie dwóch kart po VALUE_RANK
    def <=>(other)
        VALUE_RANK[rank] <=> VALUE_RANK[other.rank]
    end
    def to_s
        "#{rank}#{SUITS[suit]}"
    end
end

# Klasa reprezentująca talię kart
class Deck
    attr_reader :cards
    
    # Inicjalizuje i tasuje pełną talię
    def initialize
        @cards = Card.all.shuffle
    end

    # Rozdziela talię równomiernie na num_players graczy
    # Zwraca tablicę hands, gdzie hands[i] to stos kart gracza i
    def deal(num_players)
        hands = Array.new(num_players) { [] }
        cards.each_with_index { |card, idx| hands[idx % num_players] << card }
        hands
    end
end

# Wspólny moduł gracza (interfejs)
module Player
    attr_accessor :name, :hand
    def initialize(name); @name, @hand = name, []; end
    def has_cards?; hand.any?; end
    def play_card; hand.shift; end
    def receive_cards(c); hand.push(*c); end
    def cards_count; hand.size; end
end

# Gracz sterowany przez użytkownika w konsoli
class ConsolePlayer
    include Player
    def take_turn
        puts "#{name}, naciśnij ENTER, aby zagrać kartę (#{cards_count} kart)..."
        gets
        card = play_card
        puts "Wyłożyłeś: #{card}"
        card
    end
end

# Gracz komputer
class ComputerPlayer
    include Player
    def take_turn
        c = play_card; puts "#{name} wyłożył: #{c} (#{cards_count} kart)"; c
    end
end

class WarGame < TwoPlayerGame # Lista 10 - zamiast Game dziedziczy TwoPlayerGame
    # Przygotowanie gry: rozdzielenie kart i inicjalizacja stosu na stół
    def setup
        @pile = []
        deck = Deck.new
        hands = deck.deal(@players.size)
        @players.each_with_index { |pl, i| pl.hand = hands[i] }
    end

    def before_round # Lista 10
        if @players.any? { |pl| pl.is_a?(ConsolePlayer) }
            puts "Aby zapisać grę, wpisz 'save <plik>' lub naciśnij ENTER"
            cmd = STDIN.gets.chomp
            if cmd =~ /^save\s+(.+)/
                save_to_file($1); puts "Zapisano do #{$1}. Restartuj, aby wczytać."; exit
            end
        end
    end

    def play_round # Lista 10
        cards = @players.map(&:take_turn) # Każdy gracz zwraca zagrane karty
        @pile.concat(cards)               # Dodajemy je do stosu na stole
        compare_and_resolve(@players.zip(cards))
    end

    # Warunek zakończenia: jeśli któryś gracz straci wszystkie karty
    def game_over?
        @players.any? { |pl| !pl.has_cards? }
    end

    # Ogłoszenie zwycięzcy po zakończeniu gry
    def announce_winner
        winner = @players.max_by(&:cards_count)
        puts "Koniec gry! Zwycięzca: #{winner.name} (#{winner.cards_count} kart)"
    end

    private

    # Porównanie zagranych kart i obsługa wojny w przypadku remisu
    def compare_and_resolve(pairs)
        max_card = pairs.map(&:last).max
        winners = pairs.select { |_, card| card == max_card }.map(&:first)
        if winners.size > 1
            puts "*** WOJNA! ***"
            war(winners)
        else
            winner = winners.first
            puts "#{winner.name} zbiera #{@pile.size} kart."
            winner.receive_cards(@pile)
            @pile.clear
        end
    end

    # Obsługa wojny: każdy gracz kładzie jedną zakrytą i jedną odkrytą kartę
    def war(ct)
        w_cards=[]
        ct.each do |pl|
        # Jeśli gracz nie ma wystarczająco kart, automatycznie przegrywa
        if pl.cards_count<2
            other=(ct-[pl]).first;
            puts "#{pl.name} bez kart. #{other.name} zbiera wszystko.";
            other.receive_cards(@pile + w_cards + pl.hand); 
            pl.hand.clear;
            @pile.clear; 
            return
        end
            fdown = pl.play_card; # karta zakryta
            fup = pl.play_card;   # karta odkryta do porównania
            w_cards += [fdown,fup]; 
            puts "#{pl.name} kładzie zakrytą i odkrytą: #{fup}";
        end
        @pile.concat(w_cards)
        # Kolejna faza porównania tylko kart odkrytych
        compare_and_resolve(ct.map { |pl| [pl, w_cards.pop] })
    end
end

# Uruchomienie gry
if __FILE__ == $0
    puts "=== Gra w Wojnę ==="
    print "Wczytać zapis gry? (t/n): "
    if gets.chomp == 't'
        print "Plik: ";
        fname = gets.chomp;
        game = WarGame.load_from_file(fname)
    else
        print "Nick: ";
        n = gets.chomp;
        p1 = ConsolePlayer.new(n);
        p2 = ComputerPlayer.new("Komputer");
        game = WarGame.new([p1,p2])
    end
    game.start
end
