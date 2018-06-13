/*
 * Copyright 2002-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.security.oauth2.resourceserver.authentication;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * An implementation of an {@link AbstractOAuth2TokenAuthenticationToken}
 * representing a JSON Web Token (JWT) {@code Authentication}.
 *
 * @author Joe Grandja
 * @since 5.1
 * @see AbstractOAuth2TokenAuthenticationToken
 * @see Jwt
 */
public class JwtAuthenticationToken extends AbstractOAuth2TokenAuthenticationToken<Jwt> {
	/**
	 * Constructs a {@code JwtAuthenticationToken} using the provided parameters.
	 *
	 * @param jwt the JWT
	 */
	public JwtAuthenticationToken(Jwt jwt) {
		this(jwt, null);
	}

	/**
	 * Constructs a {@code JwtAuthenticationToken} using the provided parameters.
	 *
	 * @param jwt the JWT
	 * @param authorities the authorities assigned to the JWT
	 */
	public JwtAuthenticationToken(Jwt jwt, Collection<? extends GrantedAuthority> authorities) {
		super(jwt, authorities);
		this.setAuthenticated(true);
	}

	@Override
	public Map<String, Object> getTokenAttributes() {
		return this.getToken().getClaims();
	}

	@Override
	public String getName() {
		return Optional
				.ofNullable(this.getToken().getSubject())
				.orElse(super.getName());
	}
}
