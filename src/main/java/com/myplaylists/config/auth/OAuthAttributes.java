package com.myplaylists.config.auth;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import com.myplaylists.domain.User;

@Getter
public class OAuthAttributes {
   private Map<String, Object> attributes;
   private String nameAttributeKey;
   private String name;
   private String email;

   @Builder
   public OAuthAttributes(Map<String, Object> attributes,
                          String nameAttributeKey, String name,
                          String email) {
       this.attributes = attributes;
       this.nameAttributeKey= nameAttributeKey;
       this.name = name;
       this.email = email;
   }

   public static OAuthAttributes of(String registrationId,
                                    String userNameAttributeName,
                                    Map<String, Object> attributes) {
	   if("kakao".equals(registrationId)) {
		   return ofKakao("id", attributes);
	   }
       return ofGoogle(userNameAttributeName, attributes);
   }

   private static OAuthAttributes ofKakao(String userNameAttributeName, Map<String, Object> attributes) {
	   Map<String,Object> response = (Map<String, Object>)attributes.get("kakao_account");
	   Map<String, Object> profile = (Map<String, Object>) response.get("profile");
	   return OAuthAttributes.builder()
			   .name((String)profile.get("nickname"))
			   .email((String)response.get("email"))
			   .attributes(attributes)
			   .nameAttributeKey(userNameAttributeName)
			   .build();
   }


	private static OAuthAttributes ofGoogle(String userNameAttributeName,
                                           Map<String, Object> attributes) {
       return OAuthAttributes.builder()
               .name((String) attributes.get("name"))
               .email((String) attributes.get("email"))
               .attributes(attributes)
               .nameAttributeKey(userNameAttributeName)
               .build();
   }


   public User toEntity() {
       return User.builder()
               .name(name)
               .email(email)
               .build();
   }
}