package com.karol.hotelreservationsystem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "token")
//@Builder
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String token;
    private boolean loggedOut;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;

    // Poprzednio wzorzec Builder został zaimplementowany przy pomocy adnotacji @Builder z biblioteki Lombok
    public static TokenBuilder builder() {
        return new TokenBuilder();
    }

    public static class TokenBuilder {
        private Integer id;
        private String token;
        private boolean loggedOut;
        private User user;

        TokenBuilder() {
        }

        public TokenBuilder id(Integer id) {
            this.id = id;
            return this;
        }

        public TokenBuilder token(String token) {
            this.token = token;
            return this;
        }

        public TokenBuilder loggedOut(boolean loggedOut) {
            this.loggedOut = loggedOut;
            return this;
        }

        @JsonIgnore
        public TokenBuilder user(User user) {
            this.user = user;
            return this;
        }

        public Token build() {
            return new Token(this.id, this.token, this.loggedOut, this.user);
        }

        public String toString() {
            return "Token.TokenBuilder(id=" + this.id + ", token=" + this.token + ", loggedOut=" + this.loggedOut + ", user=" + this.user + ")";
        }
    }
}
