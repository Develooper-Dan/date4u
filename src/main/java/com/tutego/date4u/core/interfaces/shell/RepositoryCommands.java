package com.tutego.date4u.core.interfaces.shell;

import com.tutego.date4u.core.profile.Profile;
import com.tutego.date4u.core.profile.ProfileRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.util.Lazy;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@ShellComponent
public class RepositoryCommands {
  private static final int PAGE_SIZE = 10;
  private final ProfileRepository profiles;
  private Lazy<Page<Profile>> currentPage;

  public RepositoryCommands( ProfileRepository profiles ) {
    this.profiles = profiles;
    currentPage = Lazy.of( () -> profiles.findAll( PageRequest.ofSize( PAGE_SIZE ) ) );
  }

  @ShellMethod( "Display all profiles" )
  public List<Profile> list() {
    return currentPage.get().getContent();
  }

  @ShellMethod( "Set current page to previous page, display the current page" )
  List<Profile> pp() {
    currentPage = currentPage.map( page -> profiles.findAll( page.previousOrFirstPageable() ) );
    return list();
  }

  @ShellMethod( "Set current page to next page, display the current page" )
  List<Profile> np() {
    currentPage = currentPage.map( page -> profiles.findAll( page.nextOrLastPageable() ) );
    return list();
  }
  @ShellMethod( "Display likes information" )
  @Transactional
  public void likes( long id ) {
    Optional.ofNullable( profiles.findById(id))
            .ifPresent( profile -> {
              System.out.println( profile.get().getProfilesThatILike() );
              System.out.println( profile.get().getProfilesThatLikeMe() );
            } );
  }
}